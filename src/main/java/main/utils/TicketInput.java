package main.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketInput {
    // General fields
    private String type;
    private String title;
    private String businessPriority;
    private String expertiseArea;
    private String description;
    private String reportedBy;
    private String status;

    // Bug-specific fields
    private String expectedBehavior;
    private String actualBehavior;
    private String severity;
    private String frequency;
    private String environment;
    private Integer errorCode;

    // Feature Request fields
    private String customerDemand;

    // UI Feedback fields
    private String uiElementId;
    private Integer usabilityScore;
    private String screenshotUrl;
    private String suggestedFix;

    // Feature Request/UI feedback fields
    private String businessValue;
}