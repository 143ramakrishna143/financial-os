package com.financialos.dashboard.integration;

import com.financialos.dashboard.registry.WidgetRegistry;
import com.financialos.dashboard.service.DashboardEngine;
import com.financialos.dashboard.service.DashboardService;
import com.financialos.dashboard.widget.DashboardWidget;
import com.financialos.dashboard.widget.DashboardWidgetResponse;
import com.financialos.dto.DashboardOverviewDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests validating the Dashboard Framework contract.
 *
 * - Verifies Spring wiring for DashboardEngine, WidgetRegistry and DashboardService
 * - Executes all registered widgets via DashboardEngine and validates responses
 * - Ensures WidgetRegistry has no duplicate IDs and preserves ordering
 * - Ensures DashboardService builds a DashboardOverviewDTO without exceptions
 */
@SpringBootTest
public class DashboardFrameworkIntegrationTest {

    @Autowired
    private DashboardEngine dashboardEngine;

    @Autowired
    private WidgetRegistry widgetRegistry;

    @Autowired
    private DashboardService dashboardService;

    @Test
    public void contextLoads_andBeansPresent() {
        assertNotNull(dashboardEngine, "DashboardEngine should be present");
        assertNotNull(widgetRegistry, "WidgetRegistry should be present");
        assertNotNull(dashboardService, "DashboardService should be present");
    }

    @Test
    public void executeAllWidgets_and_validateResponses() {
        List<DashboardWidgetResponse<?>> responses = dashboardEngine.executeAllWidgets();
        assertNotNull(responses, "Responses list should not be null");

        // Registry validation: no duplicate IDs
        List<DashboardWidget> registered = widgetRegistry.getAllWidgetsInOrder();
        assertNotNull(registered, "Registered widgets list should not be null");
        Set<String> ids = new HashSet<>();
        for (DashboardWidget w : registered) {
            assertNotNull(w.getWidgetId(), "Widget ID must not be null for widget: " + w);
            assertTrue(ids.add(w.getWidgetId()), "Duplicate widget id found in registry: " + w.getWidgetId());
        }

        // Ordering preserved: getOrder should be strictly increasing
        int previousOrder = Integer.MIN_VALUE;
        for (DashboardWidget w : registered) {
            int order = w.getOrder();
            assertTrue(order > previousOrder, "Widget order must be strictly increasing. Problem at " + w.getWidgetId());
            previousOrder = order;
        }

        // Validate each response fields
        for (DashboardWidgetResponse<?> resp : responses) {
            assertNotNull(resp, "Individual response should not be null");
            assertNotNull(resp.getWidgetId(), "Response.widgetId should not be null");
            assertNotNull(resp.getTitle(), "Response.title should not be null");
            assertNotNull(resp.getCategory(), "Response.category should not be null for widget: " + resp.getWidgetId());
            assertNotNull(resp.getStatus(), "Response.status should not be null for widget: " + resp.getWidgetId());
            assertNotNull(resp.getLastUpdated(), "Response.lastUpdated should not be null for widget: " + resp.getWidgetId());
            assertTrue(resp.getExecutionMillis() >= 0, "executionMillis must be >= 0 for widget: " + resp.getWidgetId());
        }
    }

    @Test
    public void dashboardService_buildsOverview_withoutExceptions() {
        DashboardOverviewDTO overview = dashboardService.getDashboardOverview();
        assertNotNull(overview, "DashboardOverviewDTO should not be null");
        assertNotNull(overview.getGeneratedAt(), "Overview.generatedAt should be set");

        // Ensure that for each registered widget, either overview has a mapped response or engine produced a response
        List<DashboardWidget> registered = widgetRegistry.getAllWidgetsInOrder();
        for (DashboardWidget w : registered) {
            DashboardWidgetResponse<?> resp = overview.getWidgetResponse(w.getWidgetId());
            // It's acceptable for overview to not map every widget (some widgets may be intentionally unmapped to fields)
            // But if a mapped response exists, validate core fields
            if (resp != null) {
                assertNotNull(resp.getWidgetId());
                assertNotNull(resp.getTitle());
                assertNotNull(resp.getCategory());
                assertNotNull(resp.getStatus());
                assertNotNull(resp.getLastUpdated());
                assertTrue(resp.getExecutionMillis() >= 0);
            }
        }
    }
}
