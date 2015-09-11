/*******************************************************************************
 * Copyright (c) 2015 University of Southampton.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     University of Southampton - initial API and implementation
 *******************************************************************************/

package org.eventb.emf.persistence.tests;

import org.eventb.core.IContextRoot;
import org.eventb.core.IEvent;
import org.eventb.core.IEventBProject;
import org.eventb.core.IMachineRoot;
import org.eventb.emf.core.context.Axiom;
import org.eventb.emf.core.context.CarrierSet;
import org.eventb.emf.core.context.Constant;
import org.eventb.emf.core.context.Context;
import org.eventb.emf.core.machine.Action;
import org.eventb.emf.core.machine.Event;
import org.eventb.emf.core.machine.Guard;
import org.eventb.emf.core.machine.Invariant;
import org.eventb.emf.core.machine.Machine;
import org.eventb.emf.core.machine.Parameter;
import org.eventb.emf.core.machine.Variable;
import org.eventb.emf.core.machine.Witness;
import org.junit.Test;
import org.rodinp.core.RodinDBException;

import ch.ethz.eventb.utils.EventBUtils;

/**
 * <p>
 *
 * </p>
 *
 * @author htson
 * @version
 * @see
 * @since
 */
public class ChannelEMFSavingTests extends AbstractEventBEMFTests {

	/**
	 * Some predefined projects.
	 */
	protected String channelPrjName = "Channel";
	protected IEventBProject channelPrj;

	/**
	 * Some predefined contexts: - message_ctx, size_ctx in channelPrj.
	 */
	protected String messageCtxName = "message_ctx";
	protected Context message_ctx;

	String sizeCtxName = "size_ctx";
	protected Context size_ctx;

	/**
	 * Some carrier sets.
	 */
	protected CarrierSet MESSAGE;

	/**
	 * Some constants.
	 */
	protected Constant max_size;

	/**
	 * Some axioms and theorems.
	 */
	protected Axiom message_ctx_axm_1;

	protected Axiom message_ctx_thm_1;

	protected Axiom size_ctx_axm_1;

	/**
	 * Some predefined machines. - channel, EO, EOIO in project basedPrj.
	 */
	protected String channelMchName = "channel";
	protected Machine channelMch;

	protected String EOMchName = "EO";
	protected Machine EOMch;

	protected String EOIOMchName = "EOIO";
	protected Machine EOIOMch;

	/**
	 * Some variables.
	 */
	protected Variable channel_s_count;

	protected Variable channel_r_count;

	protected Variable EO_s_count;

	protected Variable EO_r_count;

	protected Variable EO_channel;

	protected Variable EO_sents;

	protected Variable EO_receiveds;

	protected Variable EOIO_s_count;

	protected Variable EOIO_r_count;

	protected Variable EOIO_channel;

	protected Variable EOIO_sents;

	protected Variable EOIO_receiveds;

	/**
	 * Some invariants within machines.
	 */
	protected Invariant channel_inv_1;

	protected Invariant channel_inv_2;

	protected Invariant EO_inv_1;

	protected Invariant EO_inv_2;

	protected Invariant EO_inv_3;

	protected Invariant EO_thm_1;

	protected Invariant EO_inv_4;

	protected Invariant EO_inv_5;

	protected Invariant EO_thm_2;

	protected Invariant EO_inv_6;

	protected Invariant EO_thm_3;

	protected Invariant EOIO_inv_1;

	protected Invariant EOIO_thm_1;

	protected Invariant EOIO_thm_2;

	protected Invariant EOIO_thm_3;

	protected Invariant EOIO_thm_4;

	protected Invariant EOIO_thm_5;

	/**
	 * Some events within machines.
	 */
	protected Event channel_init;

	protected Event channel_sends;

	protected Event channel_receives;

	protected Event EO_init;

	protected Event EO_sends;

	protected Event EO_receives;

	protected Event EOIO_init;

	protected Event EOIO_sends;

	protected Event EOIO_receives;

	/**
	 * Some parameters of the events
	 */
	protected Parameter channel_sends_msg;

	protected Parameter channel_receives_msg;

	protected Parameter EO_receives_idx;

	/**
	 * Some guards within events
	 */
	protected Guard channel_sends_grd_1;

	protected Guard channel_receives_grd_1;

	protected Guard EO_sends_grd_2;

	protected Guard EO_sends_thm_1;

	protected Guard EO_receives_grd_1;

	protected Guard EOIO_receives_grd_2;

	/**
	 * Some witnesses within events
	 */
	protected Witness EO_receives_msg;

	/**
	 * Some actions within events
	 */
	protected Action channel_init_act_1;

	protected Action channel_init_act_2;

	protected Action channel_sends_act_1;

	protected Action channel_receives_act_1;

	protected Action EO_init_act_3;

	protected Action EO_init_act_4;

	protected Action EO_init_act_5;

	protected Action EO_sends_act_2;

	protected Action EO_sends_act_3;

	protected Action EO_receives_act_1;

	protected Action EO_receives_act_2;

	protected Action EO_receives_act_3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ac.soton.coda.simulator2.tests.AbstractEventBEMFTests#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// Create the project
		channelPrj = EventBUtils.createEventBProject(channelPrjName,
				nullMonitor); //$NON-NLS-1$

		// Create some contexts inside the project
		message_ctx = createContext(channelPrj, messageCtxName);
		size_ctx = createContext(channelPrj, sizeCtxName);

		// Create content of message_ctx.
		// CONTEXT message_ctx
		// SETS MESSAGE
		// AXIOMS
		// axm1: finite(MESSAGE)
		// thm1: card(MESSAGE) : NAT1
		// END
		MESSAGE = createCarrierSet(message_ctx, "MESSAGE");
		message_ctx_axm_1 = createAxiom(message_ctx, "axm1", "finite(MESSAGE)",
				false);
		message_ctx_thm_1 = createAxiom(message_ctx, "thm1",
				"card(MESSAGE) ∈ ℕ1", true);
		save(message_ctx);

		// Create content for size_ctx
		// CONTEXT size_ctx
		// CONSTANTS max_size
		// AXIOMS
		// axm1: max_size : NAT1
		// END
		max_size = createConstant(size_ctx, "max_size");
		size_ctx_axm_1 = createAxiom(size_ctx, "axm1", "max_size ∈ ℕ1", false);
		save(size_ctx);

		// Create some machines inside the projects.
		channelMch = createMachine(channelPrj, channelMchName);
		EOMch = createMachine(channelPrj, EOMchName);
		EOIOMch = createMachine(channelPrj, EOIOMchName);

		// Create content for channel.
		// MACHINE channel
		// SEES message_ctx
		// VARIABLES s_count, r_count
		// INVARIANTS
		// inv1: s_count : NAT
		// inv2: r_count : NAT
		// EVENTS
		// ...
		// END
		createSeesContextClause(channelMch, messageCtxName);
		channel_s_count = createVariable(channelMch, "s_count");
		channel_r_count = createVariable(channelMch, "r_count");
		channel_inv_1 = createInvariant(channelMch, "inv1", "s_count ∈ ℕ",
				false);
		channel_inv_2 = createInvariant(channelMch, "inv2", "r_count ∈ ℕ",
				false);

		// INITIALISATION
		// STATUS ordinary
		// BEGIN
		// act1: s_count := 0
		// act2: r_count := 0
		// END
		channel_init = createEvent(channelMch, IEvent.INITIALISATION);
		channel_init_act_1 = createAction(channel_init, "act1", "s_count ≔ 0");
		channel_init_act_2 = createAction(channel_init, "act2", "r_count ≔ 0");

		// sends
		// STATUS ordinary
		// ANY msg
		// WHERE
		// grd1 : msg : MESSAGE
		// THEN
		// act1: s_count := s_count + 1
		// END
		channel_sends = createEvent(channelMch, "sends");
		channel_sends_msg = createParameter(channel_sends, "msg");
		channel_sends_grd_1 = createGuard(channel_sends, "grd1",
				"msg ∈ MESSAGE", false);
		channel_sends_act_1 = createAction(channel_sends, "act1",
				"s_count ≔ s_count + 1");

		// receives
		// STATUS ordinary
		// ANY msg
		// WHERE
		// grd1 : msg : MESSAGE
		// THEN
		// act1: r_count := r_count + 1
		// END
		channel_receives = createEvent(channelMch, "receives");
		channel_receives_msg = createParameter(channel_receives, "msg");
		channel_receives_grd_1 = createGuard(channel_receives, "grd1",
				"msg ∈ MESSAGE", false);
		channel_receives_act_1 = createAction(channel_receives, "act1",
				"r_count ≔ r_count + 1");

		// Save channel
		save(channelMch);

		// Create content for EO.
		// MACHINE EO
		// REFINES channel
		// SEES message_ctx, size_ctx
		// VARIABLES s_count, r_count, sents, receiveds, channel
		// INVARIANTS
		// inv1: sents : 1..s_count --> MESSAGE
		// inv2: receiveds : 1..r_count >-> 1..s_count
		// inv3: ran(receiveds) \/ channel = 1..s_count
		// thm1: channel <: 1..s_count
		// inv4: ran(receiveds) /\ channel = {}
		// inv5: r_count + card(channel) = s_count
		// thm2: r_count <= s_count
		// inv6: card(channel) <= max_size
		// thm3: s_count <= r_count + max_size
		// EVENTS
		// ...
		// END
		createRefinesMachineClause(EOMch, channelMchName);
		createSeesContextClause(EOMch, messageCtxName);
		createSeesContextClause(EOMch, sizeCtxName);
		EO_s_count = createVariable(EOMch, "s_count");
		EO_r_count = createVariable(EOMch, "r_count");
		EO_sents = createVariable(EOMch, "sents");
		EO_receiveds = createVariable(EOMch, "receiveds");
		EO_channel = createVariable(EOMch, "channel");
		EO_inv_1 = createInvariant(EOMch, "inv1",
				"sents ∈ 1‥s_count → MESSAGE", false);
		EO_inv_2 = createInvariant(EOMch, "inv2",
				"receiveds ∈ 1‥r_count ↣ 1‥s_count", false);
		EO_inv_3 = createInvariant(EOMch, "inv3",
				"ran(receiveds) ∪ channel = 1‥s_count", false);
		EO_thm_1 = createInvariant(EOMch, "thm1", "channel ⊆ 1‥s_count", true);
		EO_inv_4 = createInvariant(EOMch, "inv4",
				"ran(receiveds) ∩ channel = ∅", false);
		EO_inv_5 = createInvariant(EOMch, "inv5",
				"r_count + card(channel) = s_count", false);
		EO_thm_2 = createInvariant(EOMch, "thm2", "r_count ≤ s_count", true);
		EO_inv_6 = createInvariant(EOMch, "inv6", "card(channel) ≤ max_size",
				false);
		EO_thm_3 = createInvariant(EOMch, "thm3",
				"s_count ≤ r_count + max_size", true);

		// INITIALISATION
		// extended
		// STATUS ordinary
		// BEGIN
		// act3: sents := {}
		// act4: receiveds := {}
		// act5: channel := {}
		// END
		EO_init = createEvent(EOMch, IEvent.INITIALISATION);
		EO_init.setExtended(true);
		EO_init_act_3 = createAction(EO_init, "act3", "sents ≔ ∅");
		EO_init_act_4 = createAction(EO_init, "act4", "receiveds ≔ ∅");
		EO_init_act_5 = createAction(EO_init, "act5", "channel ≔ ∅");

		// sends
		// extended
		// STATUS ordinary
		// REFINES sends
		// WHEN
		// grd2 : card(channel) /= max_size
		// thm1 : {s_count + 1 |-> msg} : 1 .. s_count + 1 +-> MESSAGE
		// THEN
		// act2: sents(s_count + 1) := msg
		// act3: channel := channel \/ {s_count + 1}
		// END
		EO_sends = createEvent(EOMch, "sends");
		EO_sends.setExtended(true);
		createRefinesEventClause(EO_sends, "sends");
		EO_sends_grd_2 = createGuard(EO_sends, "grd2",
				"card(channel) ≠ max_size", false);
		EO_sends_thm_1 = createGuard(EO_sends, "thm1",
				"{s_count + 1 ↦ msg} ∈ 1 ‥ s_count + 1 ⇸ MESSAGE", true);
		EO_sends_act_2 = createAction(EO_sends, "act2",
				"sents(s_count + 1) ≔ msg");
		EO_sends_act_3 = createAction(EO_sends, "act3",
				"channel ≔ channel ∪ {s_count + 1}");

		// receives
		// STATUS ordinary
		// REFINES receives
		// ANY idx
		// WHERE
		// grd1 : idx : channel
		// WITH
		// msg: msg = sents(idx)
		// THEN
		// act1: r_count := r_count + 1
		// act2: channel := channel \ {idx}
		// act3: receiveds(r_count + 1) := idx
		// END
		EO_receives = createEvent(EOMch, "receives");
		createRefinesEventClause(EO_receives, "receives");
		EO_receives_idx = createParameter(EO_receives, "idx");
		EO_receives_grd_1 = createGuard(EO_receives, "grd1", "idx ∈ channel",
				false);
		EO_receives_msg = createWitness(EO_receives, "msg", "msg = sents(idx)");
		EO_receives_act_1 = createAction(EO_receives, "act1",
				"r_count ≔ r_count + 1");
		EO_receives_act_2 = createAction(EO_receives, "act2",
				"channel ≔ channel ∖ {idx}");
		EO_receives_act_3 = createAction(EO_receives, "act3",
				"receiveds(r_count + 1) ≔ idx");

		// Save EO
		save(EOMch);

		// Create content for EOIO.
		// MACHINE EOIO
		// REFINES EO
		// SEES message_ctx, size_ctx
		// VARIABLES s_count, r_count, sents, receiveds, channel
		// INVARIANTS
		// inv1: ran(receiveds) = 1..r_count
		// thm1: r_count : NAT
		// thm2: ran(receiveds) \/ channel = 1..s_count
		// thm3: ran(receiveds) /\ channel = {}
		// thm4: ran(receiveds) = 1 .. r_count
		// thm5: channel = r_count + 1 .. s_count
		// EVENTS
		// ...
		// END
		createRefinesMachineClause(EOIOMch, EOMchName);
		createSeesContextClause(EOIOMch, messageCtxName);
		createSeesContextClause(EOIOMch, sizeCtxName);
		EOIO_s_count = createVariable(EOIOMch, "s_count");
		EOIO_r_count = createVariable(EOIOMch, "r_count");
		EOIO_sents = createVariable(EOIOMch, "sents");
		EOIO_receiveds = createVariable(EOIOMch, "receiveds");
		EOIO_channel = createVariable(EOIOMch, "channel");
		EOIO_inv_1 = createInvariant(EOIOMch, "inv1",
				"ran(receiveds) = 1‥r_count", false);
		EOIO_thm_1 = createInvariant(EOIOMch, "thm1", "r_count ∈ ℕ", true);
		EOIO_thm_2 = createInvariant(EOIOMch, "thm2",
				"ran(receiveds) ∪ channel = 1‥s_count", true);
		EOIO_thm_3 = createInvariant(EOIOMch, "thm3",
				"ran(receiveds) ∩ channel = ∅", true);
		EOIO_thm_4 = createInvariant(EOIOMch, "thm4",
				"ran(receiveds) = 1‥r_count", true);
		EOIO_thm_5 = createInvariant(EOIOMch, "thm5",
				"channel = r_count + 1 ‥ s_count", true);

		// INITIALISATION
		// extended
		// STATUS ordinary
		// END
		EOIO_init = createEvent(EOIOMch, IEvent.INITIALISATION);
		EOIO_init.setExtended(true);

		// sends
		// extended
		// STATUS ordinary
		// REFINES sends
		// END
		EOIO_sends = createEvent(EOIOMch, "sends");
		EOIO_sends.setExtended(true);
		createRefinesEventClause(EOIO_sends, "sends");

		// receives
		// extended
		// STATUS ordinary
		// REFINES receives
		// ANY i
		// WHERE
		// grd2: idx = r_count + 1
		// THEN
		// ...
		// END
		EOIO_receives = createEvent(EOIOMch, "receives");
		EOIO_receives.setExtended(true);
		createRefinesEventClause(EOIO_receives, "receives");
		EOIO_receives_grd_2 = createGuard(EOIO_receives, "grd2",
				"idx = r_count + 1", false);

		// Save EOIO
		save(EOIOMch);

	}

	@Test
	public void testMessageContext() {
		IContextRoot root = getRoot(message_ctx);
		testContextExtendsClauses("Incorrect EXTENDS clauses for message_ctx",
				root);
		testContextCarrierSets("Incorrect SETS for message_ctx", root,
				"MESSAGE");
		testContextConstants("Incorrect CONSTANTS for message_ctx", root);
		testContextAxioms("Incorrect AXIOMS for message_ctx", root,
				"axm1:finite(MESSAGE):false", "thm1:card(MESSAGE) ∈ ℕ1:true");
	}

	@Test
	public void testMaxSizeContext() {
		IContextRoot root = getRoot(size_ctx);
		testContextExtendsClauses("Incorrect EXTENDS clauses for size_ctx",
				root);
		testContextCarrierSets("Incorrect SETS for size_ctx", root);
		testContextConstants("Incorrect CONSTANTS for size_ctx", root,
				"max_size");
		testContextAxioms("Incorrect AXIOMS for size_ctx", root,
				"axm1:max_size ∈ ℕ1:false");
	}

	@Test
	public void testChannelMachine() {
		IMachineRoot root = getRoot(channelMch);

		testMachineRefinesClauses("Incorrect REFINES clauses for channel", root);
		testMachineSeesClauses("Incorrect SEES clauses for channel", root,
				"message_ctx");
		testMachineVariables("Incorrect VARIABLES for channel", root,
				"s_count", "r_count");
		testMachineInvariants("Incorrect INVARIANTS for channel", root,
				"inv1:s_count ∈ ℕ:false", "inv2:r_count ∈ ℕ:false");
		testMachineEvents("Incorrect EVENTS for channel", root,
				"INITIALISATION:ORDINARY:false", "sends:ORDINARY:false",
				"receives:ORDINARY:false");
		try {
			IEvent[] events = root.getEvents();

			// Test INITIALISATION
			IEvent channel_init_evt = events[0];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for INITIALISATION for channel",
					channel_init_evt);
			testEventParameters("Incorrect ANY for INITIALISATION for channel",
					channel_init_evt);
			testEventGuards("Incorrect WHERE for INITIALISATION for channel",
					channel_init_evt);
			testEventWitnesses(
					"Incorrect WITNESSES for INITIALISATION for channel",
					channel_init_evt);
			testEventActions("Incorrect THEN for INITIALISATION for channel",
					channel_init_evt, "act1:s_count ≔ 0", "act2:r_count ≔ 0");

			// Test sends
			IEvent channel_sends_evt = events[1];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for sends for channel",
					channel_sends_evt);
			testEventParameters("Incorrect ANY for sends for channel",
					channel_sends_evt, "msg");
			testEventGuards("Incorrect WHERE for sends for channel",
					channel_sends_evt, "grd1:msg ∈ MESSAGE:false");
			testEventWitnesses("Incorrect WITNESSES for sends for channel",
					channel_sends_evt);
			testEventActions("Incorrect THEN for sends for channel",
					channel_sends_evt, "act1:s_count ≔ s_count + 1");

			// Test receives
			IEvent channel_receives_evt = events[2];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for receives for channel",
					channel_receives_evt);
			testEventParameters("Incorrect ANY for receives for channel",
					channel_receives_evt, "msg");
			testEventGuards("Incorrect WHERE for receives for channel",
					channel_receives_evt, "grd1:msg ∈ MESSAGE:false");
			testEventWitnesses("Incorrect WITNESSES for receives for channel",
					channel_receives_evt);
			testEventActions("Incorrect THEN for receives for channel",
					channel_receives_evt, "act1:r_count ≔ r_count + 1");

		} catch (RodinDBException e) {
			fail("There should not be any RodinDB exception");
		}

	}

	@Test
	public void testEOMachine() {
		IMachineRoot root = getRoot(EOMch);

		testMachineRefinesClauses("Incorrect REFINES clauses for EO", root,
				"channel");
		testMachineSeesClauses("Incorrect SEES clauses for EO", root,
				"message_ctx", "size_ctx");
		testMachineVariables("Incorrect VARIABLES for EO", root, "s_count",
				"r_count", "sents", "receiveds", "channel");
		testMachineInvariants("Incorrect INVARIANTS for EO", root,
				"inv1:sents ∈ 1‥s_count → MESSAGE:false",
				"inv2:receiveds ∈ 1‥r_count ↣ 1‥s_count:false",
				"inv3:ran(receiveds) ∪ channel = 1‥s_count:false",
				"thm1:channel ⊆ 1‥s_count:true",
				"inv4:ran(receiveds) ∩ channel = ∅:false",
				"inv5:r_count + card(channel) = s_count:false",
				"thm2:r_count ≤ s_count:true",
				"inv6:card(channel) ≤ max_size:false",
				"thm3:s_count ≤ r_count + max_size:true");
		testMachineEvents("Incorrect EVENTS for EO", root,
				"INITIALISATION:ORDINARY:true", "sends:ORDINARY:true",
				"receives:ORDINARY:false");
		try {
			IEvent[] events = root.getEvents();

			// Test INITIALISATION
			IEvent EO_init_evt = events[0];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for INITIALISATION for EO",
					EO_init_evt);
			testEventParameters("Incorrect ANY for INITIALISATION for EO",
					EO_init_evt);
			testEventGuards("Incorrect WHERE for INITIALISATION for EO",
					EO_init_evt);
			testEventWitnesses("Incorrect WITNESSES for INITIALISATION for EO",
					EO_init_evt);
			testEventActions("Incorrect THEN for INITIALISATION for EO",
					EO_init_evt, "act3:sents ≔ ∅", "act4:receiveds ≔ ∅",
					"act5:channel ≔ ∅");

			// Test sends
			IEvent EO_sends_evt = events[1];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for sends for EO", EO_sends_evt,
					"sends");
			testEventParameters("Incorrect ANY for sends for EO", EO_sends_evt);
			testEventGuards("Incorrect WHERE for sends for EO", EO_sends_evt,
					"grd2:card(channel) ≠ max_size:false",
					"thm1:{s_count + 1 ↦ msg} ∈ 1 ‥ s_count + 1 ⇸ MESSAGE:true");
			testEventWitnesses("Incorrect WITNESSES for sends for EO",
					EO_sends_evt);
			testEventActions("Incorrect THEN for sends for EO", EO_sends_evt,
					"act2:sents(s_count + 1) ≔ msg",
					"act3:channel ≔ channel ∪ {s_count + 1}");

			// Test receives
			IEvent EO_receives_evt = events[2];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for receives for EO",
					EO_receives_evt, "receives");
			testEventParameters("Incorrect ANY for receives for EO",
					EO_receives_evt, "idx");
			testEventGuards("Incorrect WHERE for receives for EO",
					EO_receives_evt, "grd1:idx ∈ channel:false");
			testEventWitnesses("Incorrect WITNESSES for receives for EO",
					EO_receives_evt, "msg:msg = sents(idx)");
			testEventActions("Incorrect THEN for receives for EO",
					EO_receives_evt, "act1:r_count ≔ r_count + 1",
					"act2:channel ≔ channel ∖ {idx}",
					"act3:receiveds(r_count + 1) ≔ idx");
		} catch (RodinDBException e) {
			fail("There should not be any RodinDB exception");
		}

	}

	@Test
	public void testEOIOMachine() {
		IMachineRoot root = getRoot(EOIOMch);

		testMachineRefinesClauses("Incorrect REFINES clauses for EOIO", root,
				"EO");
		testMachineSeesClauses("Incorrect SEES clauses for EOIO", root,
				"message_ctx", "size_ctx");
		testMachineVariables("Incorrect VARIABLES for EOIO", root, "s_count",
				"r_count", "sents", "receiveds", "channel");
		testMachineInvariants("Incorrect INVARIANTS for EOIO", root,
				"inv1:ran(receiveds) = 1‥r_count:false",
				"thm1:r_count ∈ ℕ:true",
				"thm2:ran(receiveds) ∪ channel = 1‥s_count:true",
				"thm3:ran(receiveds) ∩ channel = ∅:true",
				"thm4:ran(receiveds) = 1‥r_count:true",
				"thm5:channel = r_count + 1 ‥ s_count:true");
		testMachineEvents("Incorrect EVENTS for EOIO", root,
				"INITIALISATION:ORDINARY:true", "sends:ORDINARY:true",
				"receives:ORDINARY:true");

		try {
			IEvent[] events = root.getEvents();

			// Test INITIALISATION
			IEvent EOIO_init_evt = events[0];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for INITIALISATION for EOIO",
					EOIO_init_evt);
			testEventParameters("Incorrect ANY for INITIALISATION for EOIO",
					EOIO_init_evt);
			testEventGuards("Incorrect WHERE for INITIALISATION for EOIO",
					EOIO_init_evt);
			testEventWitnesses(
					"Incorrect WITNESSES for INITIALISATION for EOIO",
					EOIO_init_evt);
			testEventActions("Incorrect THEN for INITIALISATION for EOIO",
					EOIO_init_evt);

			// Test sends
			IEvent EOIO_sends_evt = events[1];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for sends for EOIO",
					EOIO_sends_evt, "sends");
			testEventParameters("Incorrect ANY for sends for EOIO",
					EOIO_sends_evt);
			testEventGuards("Incorrect WHERE for sends for EOIO",
					EOIO_sends_evt);
			testEventWitnesses("Incorrect WITNESSES for sends for EOIO",
					EOIO_sends_evt);
			testEventActions("Incorrect THEN for sends for EOIO",
					EOIO_sends_evt);

			// Test receives
			IEvent EOIO_receives_evt = events[2];
			testEventRefinesClauses(
					"Incorrect REFINES clauses for receives for EOIO",
					EOIO_receives_evt, "receives");
			testEventParameters("Incorrect ANY for receives for EOIO",
					EOIO_receives_evt);
			testEventGuards("Incorrect WHERE for receives for EOIO",
					EOIO_receives_evt, "grd2:idx = r_count + 1:false");
			testEventWitnesses("Incorrect WITNESSES for receives for EOIO",
					EOIO_receives_evt);
			testEventActions("Incorrect THEN for receives for EOIO",
					EOIO_receives_evt);

		} catch (RodinDBException e) {
			fail("There should not be any RodinDB exception");
		}
	}

}
