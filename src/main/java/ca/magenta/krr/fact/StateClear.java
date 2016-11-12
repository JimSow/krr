package ca.magenta.krr.fact;

import java.util.HashSet;

import org.kie.api.runtime.rule.FactHandle;

import ca.magenta.krr.engine.Engine;

/**
 * @author jean-paul.laberge <jplaberge@magenta.ca>
 * @version 0.1
 * @since 2014-03-16
 */
final public class StateClear extends StateLifecycle{
	
	private transient HashSet<FactHandle> lastCauses = new HashSet<FactHandle>();
	
	public static void insertInWM(FactHandle factHandle, State newState, State oldState, HashSet<FactHandle> oldCauses, boolean firstEnteredCleared)
	{
		StateClear stateClear = new StateClear(factHandle, newState, oldState, oldCauses, firstEnteredCleared);
		
		Engine.getStreamKS().insert(stateClear);
	}

	private StateClear() {
		super();
	}

	private StateClear(FactHandle factHandle, State stateNew, State stateOld, HashSet<FactHandle> oldCauses, boolean firstEnteredCleared) {
		super();
		
		if (oldCauses != null)
			lastCauses = oldCauses;
		
		this.setFactHandleRef(factHandle);
		this.setLinkKeyRef(stateNew.getLinkKey());
		
		if (firstEnteredCleared)
			this.setChanges(new HashSet<String>());
		else
			this.setChanges(stateNew.getChanges(stateOld));
	}

	public HashSet<FactHandle> getLastCauses() {
		return lastCauses;
	}
}
