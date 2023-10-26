package com.imianammar.alarms;

public class AlarmItem {

    private String name;
    private boolean enabled;

    public AlarmItem(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }

    @Override
    public String toString() {
        return name + " (Enabled: " + enabled + ")";
    }

    public void setName(String newAlarmName) {
        this.name = newAlarmName;
    }

    public void setEnabled(boolean newIsEnabled) {
        this.enabled = newIsEnabled;
    }
}


