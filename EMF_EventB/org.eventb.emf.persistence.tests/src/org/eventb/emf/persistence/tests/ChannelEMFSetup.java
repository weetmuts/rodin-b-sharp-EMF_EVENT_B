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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eventb.core.IEvent;
import org.eventb.core.IEventBProject;
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
import org.eventb.emf.persistence.EMFRodinDB;
import org.eventb.emf.persistence.EventBEMFUtils;

import ch.ethz.eventb.utils.EventBUtils;

/**
 * <p>
 * This class is used to create a Event-B project for testing purpose through
 * the Event-B EMF APIs.
 * </p>
 *
 * @author htson
 * @version 0.1
 * @see EventBEMFUtils
 * @since 0.1
 */
public class ChannelEMFSetup {

	/**
	 * Some predefined projects.
	 */
	private final static String channelPrjName = "Channel";
	private static IEventBProject channelPrj;

	/**
	 * Some predefined contexts: - message_ctx, size_ctx in channelPrj.
	 */
	private final static String messageCtxName = "message_ctx";
	private static Context message_ctx;

	private final static String sizeCtxName = "size_ctx";
	private static Context size_ctx;

	/**
	 * Some carrier sets.
	 */
	private static CarrierSet MESSAGE;

	/**
	 * Some constants.
	 */
	private static Constant max_size;

	/**
	 * Some axioms and theorems.
	 */
	private static Axiom message_ctx_axm_1;

	private static Axiom message_ctx_thm_1;

	private static Axiom size_ctx_axm_1;

	/**
	 * Some predefined machines. - channel, EO, EOIO in project basedPrj.
	 */
	private static String channelMchName = "channel";
	private static Machine channelMch;

	private static String EOMchName = "EO";
	private static Machine EOMch;

	private static String EOIOMchName = "EOIO";
	private static Machine EOIOMch;

	/**
	 * Some variables.
	 */
	private static Variable channel_s_count;

	private static Variable channel_r_count;

	private static Variable EO_s_count;

	private static Variable EO_r_count;

	private static Variable EO_channel;

	private static Variable EO_sents;

	private static Variable EO_receiveds;

	private static Variable EOIO_s_count;

	private static Variable EOIO_r_count;

	private static Variable EOIO_channel;

	private static Variable EOIO_sents;

	private static Variable EOIO_receiveds;

	/**
	 * Some invariants within machines.
	 */
	private static Invariant channel_inv_1;

	private static Invariant channel_inv_2;

	private static Invariant EO_inv_1;

	private static Invariant EO_inv_2;

	private static Invariant EO_inv_3;

	private static Invariant EO_thm_1;

	private static Invariant EO_inv_4;

	private static Invariant EO_inv_5;

	private static Invariant EO_thm_2;

	private static Invariant EO_inv_6;

	private static Invariant EO_thm_3;

	private static Invariant EOIO_inv_1;

	private static Invariant EOIO_thm_1;

	private static Invariant EOIO_thm_2;

	private static Invariant EOIO_thm_3;

	private static Invariant EOIO_thm_4;

	private static Invariant EOIO_thm_5;

	/**
	 * Some events within machines.
	 */
	private static Event channel_init;

	private static Event channel_sends;

	private static Event channel_receives;

	private static Event EO_init;

	private static Event EO_sends;

	private static Event EO_receives;

	private static Event EOIO_init;

	private static Event EOIO_sends;

	private static Event EOIO_receives;

	/**
	 * Some parameters of the events
	 */
	private static Parameter channel_sends_msg;

	private static Parameter channel_receives_msg;

	private static Parameter EO_receives_idx;

	/**
	 * Some guards within events
	 */
	private static Guard channel_sends_grd_1;

	private static Guard channel_receives_grd_1;

	private static Guard EO_sends_grd_2;

	private static Guard EO_sends_thm_1;

	private static Guard EO_receives_grd_1;

	private static Guard EOIO_receives_grd_2;

	/**
	 * Some witnesses within events
	 */
	private static Witness EO_receives_msg;

	/**
	 * Some actions within events
	 */
	private static Action channel_init_act_1;

	private static Action channel_init_act_2;

	private static Action channel_sends_act_1;

	private static Action channel_receives_act_1;

	private static Action EO_init_act_3;

	private static Action EO_init_act_4;

	private static Action EO_init_act_5;

	private static Action EO_sends_act_2;

	private static Action EO_sends_act_3;

	private static Action EO_receives_act_1;

	private static Action EO_receives_act_2;

	private static Action EO_receives_act_3;

	public static void setup() throws CoreException {
		IProgressMonitor nullMonitor = new NullProgressMonitor();
		EMFRodinDB emfRodinDB = new EMFRodinDB();
		TransactionalEditingDomain domain = emfRodinDB.getEditingDomain();

		// Create the project
		channelPrj = EventBUtils.createEventBProject(channelPrjName,
				nullMonitor); //$NON-NLS-1$

		// Create some contexts inside the project
		message_ctx = EventBEMFUtils.createContext(emfRodinDB, channelPrj,
				messageCtxName);
		size_ctx = EventBEMFUtils.createContext(emfRodinDB, channelPrj,
				sizeCtxName);

		// Create content of message_ctx.
		// CONTEXT message_ctx
		// SETS MESSAGE
		// AXIOMS
		// axm1: finite(MESSAGE)
		// thm1: card(MESSAGE) : NAT1
		// END
		MESSAGE = EventBEMFUtils.createCarrierSet(domain, message_ctx,
				"MESSAGE");
		message_ctx_axm_1 = EventBEMFUtils.createAxiom(domain, message_ctx,
				"axm1", "finite(MESSAGE)", false);
		message_ctx_thm_1 = EventBEMFUtils.createAxiom(domain, message_ctx,
				"thm1", "card(MESSAGE) ∈ ℕ1", true);
		EventBEMFUtils.save(emfRodinDB, message_ctx);

		// Create content for size_ctx
		// CONTEXT size_ctx
		// CONSTANTS max_size
		// AXIOMS
		// axm1: max_size : NAT1
		// END
		max_size = EventBEMFUtils.createConstant(domain, size_ctx, "max_size");
		size_ctx_axm_1 = EventBEMFUtils.createAxiom(domain, size_ctx, "axm1",
				"max_size ∈ ℕ1", false);
		EventBEMFUtils.save(emfRodinDB, size_ctx);

		// Create some machines inside the projects.
		channelMch = EventBEMFUtils.createMachine(emfRodinDB, channelPrj,
				channelMchName);
		EOMch = EventBEMFUtils.createMachine(emfRodinDB, channelPrj, EOMchName);
		EOIOMch = EventBEMFUtils.createMachine(emfRodinDB, channelPrj,
				EOIOMchName);

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
		EventBEMFUtils.createSeesContextClause(domain, channelMch, message_ctx);
		channel_s_count = EventBEMFUtils.createVariable(domain, channelMch,
				"s_count");
		channel_r_count = EventBEMFUtils.createVariable(domain, channelMch,
				"r_count");
		channel_inv_1 = EventBEMFUtils.createInvariant(domain, channelMch,
				"inv1", "s_count ∈ ℕ", false);
		channel_inv_2 = EventBEMFUtils.createInvariant(domain, channelMch,
				"inv2", "r_count ∈ ℕ", false);

		// INITIALISATION
		// STATUS ordinary
		// BEGIN
		// act1: s_count := 0
		// act2: r_count := 0
		// END
		channel_init = EventBEMFUtils.createEvent(domain, channelMch,
				IEvent.INITIALISATION);
		channel_init_act_1 = EventBEMFUtils.createAction(domain, channel_init,
				"act1", "s_count ≔ 0");
		channel_init_act_2 = EventBEMFUtils.createAction(domain, channel_init,
				"act2", "r_count ≔ 0");

		// sends
		// STATUS ordinary
		// ANY msg
		// WHERE
		// grd1 : msg : MESSAGE
		// THEN
		// act1: s_count := s_count + 1
		// END
		channel_sends = EventBEMFUtils.createEvent(domain, channelMch, "sends");
		channel_sends_msg = EventBEMFUtils.createParameter(domain,
				channel_sends, "msg");
		channel_sends_grd_1 = EventBEMFUtils.createGuard(domain, channel_sends,
				"grd1", "msg ∈ MESSAGE", false);
		channel_sends_act_1 = EventBEMFUtils.createAction(domain,
				channel_sends, "act1", "s_count ≔ s_count + 1");

		// receives
		// STATUS ordinary
		// ANY msg
		// WHERE
		// grd1 : msg : MESSAGE
		// THEN
		// act1: r_count := r_count + 1
		// END
		channel_receives = EventBEMFUtils.createEvent(domain, channelMch,
				"receives");
		channel_receives_msg = EventBEMFUtils.createParameter(domain,
				channel_receives, "msg");
		channel_receives_grd_1 = EventBEMFUtils.createGuard(domain,
				channel_receives, "grd1", "msg ∈ MESSAGE", false);
		channel_receives_act_1 = EventBEMFUtils.createAction(domain,
				channel_receives, "act1", "r_count ≔ r_count + 1");

		// Save channel
		EventBEMFUtils.save(emfRodinDB, channelMch);

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
		EventBEMFUtils
				.createRefinesMachineClause(domain, EOMch, channelMchName);
		EventBEMFUtils.createSeesContextClause(domain, EOMch, message_ctx);
		EventBEMFUtils.createSeesContextClause(domain, EOMch, size_ctx);
		EO_s_count = EventBEMFUtils.createVariable(domain, EOMch, "s_count");
		EO_r_count = EventBEMFUtils.createVariable(domain, EOMch, "r_count");
		EO_sents = EventBEMFUtils.createVariable(domain, EOMch, "sents");
		EO_receiveds = EventBEMFUtils
				.createVariable(domain, EOMch, "receiveds");
		EO_channel = EventBEMFUtils.createVariable(domain, EOMch, "channel");
		EO_inv_1 = EventBEMFUtils.createInvariant(domain, EOMch, "inv1",
				"sents ∈ 1‥s_count → MESSAGE", false);
		EO_inv_2 = EventBEMFUtils.createInvariant(domain, EOMch, "inv2",
				"receiveds ∈ 1‥r_count ↣ 1‥s_count", false);
		EO_inv_3 = EventBEMFUtils.createInvariant(domain, EOMch, "inv3",
				"ran(receiveds) ∪ channel = 1‥s_count", false);
		EO_thm_1 = EventBEMFUtils.createInvariant(domain, EOMch, "thm1",
				"channel ⊆ 1‥s_count", true);
		EO_inv_4 = EventBEMFUtils.createInvariant(domain, EOMch, "inv4",
				"ran(receiveds) ∩ channel = ∅", false);
		EO_inv_5 = EventBEMFUtils.createInvariant(domain, EOMch, "inv5",
				"r_count + card(channel) = s_count", false);
		EO_thm_2 = EventBEMFUtils.createInvariant(domain, EOMch, "thm2",
				"r_count ≤ s_count", true);
		EO_inv_6 = EventBEMFUtils.createInvariant(domain, EOMch, "inv6",
				"card(channel) ≤ max_size", false);
		EO_thm_3 = EventBEMFUtils.createInvariant(domain, EOMch, "thm3",
				"s_count ≤ r_count + max_size", true);

		// INITIALISATION
		// extended
		// STATUS ordinary
		// BEGIN
		// act3: sents := {}
		// act4: receiveds := {}
		// act5: channel := {}
		// END
		EO_init = EventBEMFUtils.createEvent(domain, EOMch,
				IEvent.INITIALISATION);
		EO_init.setExtended(true);
		EO_init_act_3 = EventBEMFUtils.createAction(domain, EO_init, "act3",
				"sents ≔ ∅");
		EO_init_act_4 = EventBEMFUtils.createAction(domain, EO_init, "act4",
				"receiveds ≔ ∅");
		EO_init_act_5 = EventBEMFUtils.createAction(domain, EO_init, "act5",
				"channel ≔ ∅");

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
		EO_sends = EventBEMFUtils.createEvent(domain, EOMch, "sends");
		EO_sends.setExtended(true);
		EventBEMFUtils.createRefinesEventClause(domain, EO_sends, "sends");
		EO_sends_grd_2 = EventBEMFUtils.createGuard(domain, EO_sends, "grd2",
				"card(channel) ≠ max_size", false);
		EO_sends_thm_1 = EventBEMFUtils.createGuard(domain, EO_sends, "thm1",
				"{s_count + 1 ↦ msg} ∈ 1 ‥ s_count + 1 ⇸ MESSAGE", true);
		EO_sends_act_2 = EventBEMFUtils.createAction(domain, EO_sends, "act2",
				"sents(s_count + 1) ≔ msg");
		EO_sends_act_3 = EventBEMFUtils.createAction(domain, EO_sends, "act3",
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
		EO_receives = EventBEMFUtils.createEvent(domain, EOMch, "receives");
		EventBEMFUtils
				.createRefinesEventClause(domain, EO_receives, "receives");
		EO_receives_idx = EventBEMFUtils.createParameter(domain, EO_receives,
				"idx");
		EO_receives_grd_1 = EventBEMFUtils.createGuard(domain, EO_receives,
				"grd1", "idx ∈ channel", false);
		EO_receives_msg = EventBEMFUtils.createWitness(domain, EO_receives,
				"msg", "msg = sents(idx)");
		EO_receives_act_1 = EventBEMFUtils.createAction(domain, EO_receives,
				"act1", "r_count ≔ r_count + 1");
		EO_receives_act_2 = EventBEMFUtils.createAction(domain, EO_receives,
				"act2", "channel ≔ channel ∖ {idx}");
		EO_receives_act_3 = EventBEMFUtils.createAction(domain, EO_receives,
				"act3", "receiveds(r_count + 1) ≔ idx");

		// Save EO
		EventBEMFUtils.save(emfRodinDB, EOMch);

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
		EventBEMFUtils.createRefinesMachineClause(domain, EOIOMch, EOMchName);
		EventBEMFUtils.createSeesContextClause(domain, EOIOMch, message_ctx);
		EventBEMFUtils.createSeesContextClause(domain, EOIOMch, size_ctx);
		EOIO_s_count = EventBEMFUtils
				.createVariable(domain, EOIOMch, "s_count");
		EOIO_r_count = EventBEMFUtils
				.createVariable(domain, EOIOMch, "r_count");
		EOIO_sents = EventBEMFUtils.createVariable(domain, EOIOMch, "sents");
		EOIO_receiveds = EventBEMFUtils.createVariable(domain, EOIOMch,
				"receiveds");
		EOIO_channel = EventBEMFUtils
				.createVariable(domain, EOIOMch, "channel");
		EOIO_inv_1 = EventBEMFUtils.createInvariant(domain, EOIOMch, "inv1",
				"ran(receiveds) = 1‥r_count", false);
		EOIO_thm_1 = EventBEMFUtils.createInvariant(domain, EOIOMch, "thm1",
				"r_count ∈ ℕ", true);
		EOIO_thm_2 = EventBEMFUtils.createInvariant(domain, EOIOMch, "thm2",
				"ran(receiveds) ∪ channel = 1‥s_count", true);
		EOIO_thm_3 = EventBEMFUtils.createInvariant(domain, EOIOMch, "thm3",
				"ran(receiveds) ∩ channel = ∅", true);
		EOIO_thm_4 = EventBEMFUtils.createInvariant(domain, EOIOMch, "thm4",
				"ran(receiveds) = 1‥r_count", true);
		EOIO_thm_5 = EventBEMFUtils.createInvariant(domain, EOIOMch, "thm5",
				"channel = r_count + 1 ‥ s_count", true);

		// INITIALISATION
		// extended
		// STATUS ordinary
		// END
		EOIO_init = EventBEMFUtils.createEvent(domain, EOIOMch,
				IEvent.INITIALISATION);
		EOIO_init.setExtended(true);

		// sends
		// extended
		// STATUS ordinary
		// REFINES sends
		// END
		EOIO_sends = EventBEMFUtils.createEvent(domain, EOIOMch, "sends");
		EOIO_sends.setExtended(true);
		EventBEMFUtils.createRefinesEventClause(domain, EOIO_sends, "sends");

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
		EOIO_receives = EventBEMFUtils.createEvent(domain, EOIOMch, "receives");
		EOIO_receives.setExtended(true);
		EventBEMFUtils.createRefinesEventClause(domain, EOIO_receives,
				"receives");
		EOIO_receives_grd_2 = EventBEMFUtils.createGuard(domain, EOIO_receives,
				"grd2", "idx = r_count + 1", false);

		// Save EOIO
		EventBEMFUtils.save(emfRodinDB, EOIOMch);
	}

	/**
	 * @return
	 */
	public static Context getMessageContext() {
		return message_ctx;
	}

	/**
	 * @return
	 */
	public static Context getSizeContext() {
		return size_ctx;
	}

	/**
	 * @return
	 */
	public static Machine getChannelMachine() {
		return channelMch;
	}

	/**
	 * @return
	 */
	public static Machine getEOMachine() {
		return EOMch;
	}

	/**
	 * @return
	 */
	public static Machine getEOIOMachine() {
		return EOIOMch;
	}

	/**
	 * @return
	 */
	public static CarrierSet getMessageCarrierSet() {
		return MESSAGE;
	}

	/**
	 * @return
	 */
	public static Constant getSizeConstant() {
		return max_size;
	}

	/**
	 * @return
	 */
	public static Variable getEOChannelVariable() {
		return EO_channel;
	}

	/**
	 * @return
	 */
	public static Event getChannelInitEvent() {
		return channel_init;
	}

	/**
	 * @return
	 */
	public static Event getChannelSendsEvent() {
		return channel_sends;
	}

}
