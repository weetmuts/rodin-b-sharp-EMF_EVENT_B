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

package org.eventb.emf.internal.persistence.tests;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eventb.emf.core.context.Context;
import org.eventb.emf.core.machine.Event;
import org.eventb.emf.core.machine.Machine;
import org.eventb.emf.persistence.tests.AbstractEventBEMFTests;
import org.junit.Test;

import ch.ethz.eventb.utils.tests.ChannelSetup;

/**
 * <p>
 * This class tests the loading Rodin DB resources into Event-B EMF elements.
 * </p>
 *
 * @author htson
 * @version 0.1
 * @see ChannelSetup
 * @since 0.1
 */
public class ChannelEMFLoadingTests extends AbstractEventBEMFTests {

	/**
	 * The loaded <code>message_ctx</code> context.
	 */
	private Context messageCtx;

	/**
	 * The loaded <code>size_ctx</code> context.
	 */
	private Context sizeCtx;

	/**
	 * The loaded <code>channel</code> machine.
	 */
	private Machine channelMch;

	/**
	 * The loaded <code>EO</code> machine.
	 */
	private Machine EOMchRoot;

	/**
	 * The loaded <code>EOIO</code> machine.
	 */
	private Machine EOIOMchRoot;

	/**
	 * This class performs the following tasks:
	 * <ol>
	 * <li>Calls the super method.</li>
	 * 
	 * <li>Setups a Channel project using the RodinDB APIs.</li>
	 * 
	 * <li>Loads the handles of various components in the Channel project into
	 * Event-B EMF elements.</li>
	 * </ol>
	 * 
	 * @see AbstractEventBEMFTests#setUp()
	 * @see ChannelSetup
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ChannelSetup.setup();
		messageCtx = (Context) emfRodinDB.loadEventBComponent(ChannelSetup
				.getMessageContextRoot());

		sizeCtx = (Context) emfRodinDB.loadEventBComponent(ChannelSetup
				.getSizeContextRoot());

		channelMch = (Machine) emfRodinDB.loadEventBComponent(ChannelSetup
				.getChannelMachineRoot());
		EOMchRoot = (Machine) emfRodinDB.loadEventBComponent(ChannelSetup
				.getEOMachineRoot());
		EOIOMchRoot = (Machine) emfRodinDB.loadEventBComponent(ChannelSetup
				.getEOIOMachineRoot());
	}

	/**
	 * Test the loaded context <code>message_ctx</code>.
	 */
	@Test
	public void testMessageContext() {
		testContextExtendsClauses("Incorrect EXTENDS clauses for message_ctx",
				messageCtx);
		testContextCarrierSets("Incorrect SETS for message_ctx", messageCtx,
				"MESSAGE");
		testContextConstants("Incorrect CONSTANTS for message_ctx", messageCtx);
		testContextAxioms("Incorrect AXIOMS for message_ctx", messageCtx,
				"axm1:finite(MESSAGE):false", "thm1:card(MESSAGE) ∈ ℕ1:true");
	}

	/**
	 * Test the loaded context <code>size_ctx</code>.
	 */
	@Test
	public void testMaxSizeContext() {
		testContextExtendsClauses("Incorrect EXTENDS clauses for size_ctx",
				sizeCtx);
		testContextCarrierSets("Incorrect SETS for size_ctx", sizeCtx);
		testContextConstants("Incorrect CONSTANTS for size_ctx", sizeCtx,
				"max_size");
		testContextAxioms("Incorrect AXIOMS for size_ctx", sizeCtx,
				"axm1:max_size ∈ ℕ1:false");
	}

	/**
	 * Test the loaded machine <code>channel</code>.
	 */
	@Test
	public void testChannelMachine() {
		testMachineRefinesClauses("Incorrect REFINES clauses for channel",
				channelMch);
		testMachineSeesClauses("Incorrect SEES clauses for channel",
				channelMch, "message_ctx");
		testMachineVariables("Incorrect VARIABLES for channel", channelMch,
				"s_count", "r_count");
		testMachineInvariants("Incorrect INVARIANTS for channel", channelMch,
				"inv1:s_count ∈ ℕ:false", "inv2:r_count ∈ ℕ:false");
		testMachineEvents("Incorrect EVENTS for channel", channelMch,
				"INITIALISATION:ordinary:false", "sends:ordinary:false",
				"receives:ordinary:false");
		EList<Event> evts = channelMch.getEvents();
		Iterator<Event> iterator = evts.iterator();

		// Test INITIALISATION
		Event channel_init_evt = iterator.next();
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
		Event channel_sends_evt = iterator.next();
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
		Event channel_receives_evt = iterator.next();
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

	}

	/**
	 * Test the loaded machine <code>EO</code>.
	 */
	@Test
	public void testEOMachine() {
		testMachineRefinesClauses("Incorrect REFINES clauses for EO",
				EOMchRoot, "channel");
		testMachineSeesClauses("Incorrect SEES clauses for EO", EOMchRoot,
				"message_ctx", "size_ctx");
		testMachineVariables("Incorrect VARIABLES for EO", EOMchRoot,
				"s_count", "r_count", "sents", "receiveds", "channel");
		testMachineInvariants("Incorrect INVARIANTS for EO", EOMchRoot,
				"inv1:sents ∈ 1‥s_count → MESSAGE:false",
				"inv2:receiveds ∈ 1‥r_count ↣ 1‥s_count:false",
				"inv3:ran(receiveds) ∪ channel = 1‥s_count:false",
				"thm1:channel ⊆ 1‥s_count:true",
				"inv4:ran(receiveds) ∩ channel = ∅:false",
				"inv5:r_count + card(channel) = s_count:false",
				"thm2:r_count ≤ s_count:true",
				"inv6:card(channel) ≤ max_size:false",
				"thm3:s_count ≤ r_count + max_size:true");
		testMachineEvents("Incorrect EVENTS for EO", EOMchRoot,
				"INITIALISATION:ordinary:true", "sends:ordinary:true",
				"receives:ordinary:false");

		EList<Event> events = EOMchRoot.getEvents();
		Iterator<Event> iterator = events.iterator();

		// Test INITIALISATION
		Event EO_init_evt = iterator.next();
		// The refines clause is added automatically?
		testEventRefinesClauses(
				"Incorrect REFINES clauses for INITIALISATION for EO",
				EO_init_evt, "INITIALISATION");
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
		Event EO_sends_evt = iterator.next();
		testEventRefinesClauses("Incorrect REFINES clauses for sends for EO",
				EO_sends_evt, "sends");
		testEventParameters("Incorrect ANY for sends for EO", EO_sends_evt);
		testEventGuards("Incorrect WHERE for sends for EO", EO_sends_evt,
				"grd2:card(channel) ≠ max_size:false",
				"thm1:{s_count + 1 ↦ msg} ∈ 1 ‥ s_count + 1 ⇸ MESSAGE:true");
		testEventWitnesses("Incorrect WITNESSES for sends for EO", EO_sends_evt);
		testEventActions("Incorrect THEN for sends for EO", EO_sends_evt,
				"act2:sents(s_count + 1) ≔ msg",
				"act3:channel ≔ channel ∪ {s_count + 1}");

		// Test receives
		Event EO_receives_evt = iterator.next();
		testEventRefinesClauses(
				"Incorrect REFINES clauses for receives for EO",
				EO_receives_evt, "receives");
		testEventParameters("Incorrect ANY for receives for EO",
				EO_receives_evt, "idx");
		testEventGuards("Incorrect WHERE for receives for EO", EO_receives_evt,
				"grd1:idx ∈ channel:false");
		testEventWitnesses("Incorrect WITNESSES for receives for EO",
				EO_receives_evt, "msg:msg = sents(idx)");
		testEventActions("Incorrect THEN for receives for EO", EO_receives_evt,
				"act1:r_count ≔ r_count + 1", "act2:channel ≔ channel ∖ {idx}",
				"act3:receiveds(r_count + 1) ≔ idx");

	}

	/**
	 * Test the loaded machine <code>EOIO</code>.
	 */
	@Test
	public void testEOIOMachine() {
		testMachineRefinesClauses("Incorrect REFINES clauses for EOIO",
				EOIOMchRoot, "EO");
		testMachineSeesClauses("Incorrect SEES clauses for EOIO", EOIOMchRoot,
				"message_ctx", "size_ctx");
		testMachineVariables("Incorrect VARIABLES for EOIO", EOIOMchRoot,
				"s_count", "r_count", "sents", "receiveds", "channel");
		testMachineInvariants("Incorrect INVARIANTS for EOIO", EOIOMchRoot,
				"inv1:ran(receiveds) = 1‥r_count:false",
				"thm1:r_count ∈ ℕ:true",
				"thm2:ran(receiveds) ∪ channel = 1‥s_count:true",
				"thm3:ran(receiveds) ∩ channel = ∅:true",
				"thm4:ran(receiveds) = 1‥r_count:true",
				"thm5:channel = r_count + 1 ‥ s_count:true");
		testMachineEvents("Incorrect EVENTS for EOIO", EOIOMchRoot,
				"INITIALISATION:ordinary:true", "sends:ordinary:true",
				"receives:ordinary:true");

		EList<Event> events = EOIOMchRoot.getEvents();
		Iterator<Event> iterator = events.iterator();

		// Test INITIALISATION
		Event EOIO_init_evt = iterator.next();
		// The refines clause is added automatically?
		testEventRefinesClauses(
				"Incorrect REFINES clauses for INITIALISATION for EOIO",
				EOIO_init_evt, "INITIALISATION");
		testEventParameters("Incorrect ANY for INITIALISATION for EOIO",
				EOIO_init_evt);
		testEventGuards("Incorrect WHERE for INITIALISATION for EOIO",
				EOIO_init_evt);
		testEventWitnesses("Incorrect WITNESSES for INITIALISATION for EOIO",
				EOIO_init_evt);
		testEventActions("Incorrect THEN for INITIALISATION for EOIO",
				EOIO_init_evt);

		// Test sends
		Event EOIO_sends_evt = iterator.next();
		testEventRefinesClauses("Incorrect REFINES clauses for sends for EOIO",
				EOIO_sends_evt, "sends");
		testEventParameters("Incorrect ANY for sends for EOIO", EOIO_sends_evt);
		testEventGuards("Incorrect WHERE for sends for EOIO", EOIO_sends_evt);
		testEventWitnesses("Incorrect WITNESSES for sends for EOIO",
				EOIO_sends_evt);
		testEventActions("Incorrect THEN for sends for EOIO", EOIO_sends_evt);

		// Test receives
		Event EOIO_receives_evt = iterator.next();
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

	}

}
