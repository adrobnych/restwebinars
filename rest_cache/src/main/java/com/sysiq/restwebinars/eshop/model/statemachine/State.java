package com.sysiq.restwebinars.eshop.model.statemachine;

import java.util.Map;

public class State {
	Map<String, Action> actionsAvailable;

	public Map<String, Action> getAvailableActions() {
		return actionsAvailable;
	}
}
