package org.tigersndragons.salonbooks.model.flows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

public class SalonFlowHandler extends AbstractFlowHandler {

	@Override
	public String getFlowId() {
		// TODO Auto-generated method stub
		return super.getFlowId();
	}

	@Override
	public MutableAttributeMap<Object> createExecutionInputMap(
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.createExecutionInputMap(request);
	}

	@Override
	public String handleExecutionOutcome(FlowExecutionOutcome outcome,
			HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return super.handleExecutionOutcome(outcome, request, response);
	}

	@Override
	public String handleException(FlowException e, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return super.handleException(e, request, response);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	

}
