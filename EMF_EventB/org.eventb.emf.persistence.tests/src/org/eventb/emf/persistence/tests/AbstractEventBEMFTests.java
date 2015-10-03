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

import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
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
import org.eventb.emf.core.machine.Variant;
import org.eventb.emf.core.machine.Witness;
import org.eventb.emf.persistence.EMFRodinDB;
import org.junit.Before;

import ch.ethz.eventb.utils.tests.AbstractEventBTests;

/**
 * <p>
 * This is the abstract class for Unit tests involving Event-B EMF. This class
 * contains several method for testing the contents of Event-B EMF elements.
 * </p>
 *
 * @author htson
 * @version 0.1
 * @see AbstractEventBTests
 * @since 0.1
 */
public abstract class AbstractEventBEMFTests extends AbstractEventBTests {

	/**
	 * The EMFRodinDB instance that can be used to create and modify Event-B EMF
	 * elements. This is created in the {@link #setUp()} method.
	 */
	protected EMFRodinDB emfRodinDB;

	/**
	 * The EMF transactional editing domain corresponds to {@link #emfRodinDB}.
	 */
	protected TransactionalEditingDomain domain;

	/**
	 * This setup method performs the following
	 * <ol>
	 * <li>Call the super method.</li>
	 * 
	 * <li>Create the EMFRodinDB instance and get the transactional editing
	 * domain.</li>
	 * </ol>
	 * 
	 * @see TestCase#setUp()
	 */
	@Before
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		emfRodinDB = new EMFRodinDB();
		domain = emfRodinDB.getEditingDomain();
	}

	// =========================================================================
	// (BEGIN) Utility methods for testing Context elements.
	// =========================================================================

	/**
	 * Utility method for testing EXTENDS clauses of a context.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param ctx
	 *            A context root whose EXTENDS clauses will be tested.
	 * @param expected
	 *            the array of expected EXTENDS clauses. Each clause is
	 *            represented by the abstract context name. The order of the
	 *            EXTENDS clause is important.
	 */
	protected void testContextExtendsClauses(String msg, Context ctx,
			String... expected) {
		EList<String> extendsNames = ctx.getExtendsNames();
		assertSameStrings(msg,
				extendsNames.toArray(new String[extendsNames.size()]), expected);
	}

	/**
	 * Utility method for testing the carrier sets of a context.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param ctx
	 *            a context whose carrier sets will be tested.
	 * @param expected
	 *            an array of expected carrier sets. Each carrier set is
	 *            represented by its identifier. The order of the carrier sets
	 *            is important.
	 */
	protected void testContextCarrierSets(String msg, Context ctx,
			String... expected) {
		EList<CarrierSet> sets = ctx.getSets();
		assertEquals(msg + ": Incorrect number of carrier sets",
				expected.length, sets.size());
		Iterator<CarrierSet> iterator = sets.iterator();
		for (int i = 0; i < expected.length; i++) {
			testCarrierSet(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing a carrier set.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param set
	 *            the carrier set under test.
	 * @param expected
	 *            the expected identifier of the carrier set.
	 */
	protected void testCarrierSet(String msg, CarrierSet set, String expected) {
		assertEquals(msg + ": Incorrect carrier set", expected, set.getName());
	}

	/**
	 * Utility method for testing the constants of a context.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param ctx
	 *            a context whose constants will be tested.
	 * @param expected
	 *            an array of expected constants. Each constant is represented
	 *            by its identifier. The order of the constants is important.
	 */
	protected void testContextConstants(String msg, Context ctx,
			String... expected) {
		EList<Constant> csts = ctx.getConstants();
		assertEquals(msg + ": Incorrect number of constants", expected.length,
				csts.size());
		Iterator<Constant> iterator = csts.iterator();
		for (int i = 0; i < expected.length; i++) {
			testConstant(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing a constant.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param cst
	 *            the constant under test.
	 * @param expected
	 *            the expected identifier of the constant.
	 */
	protected void testConstant(String msg, Constant cst, String expected) {
		assertEquals(msg + ": Incorrect constant", expected, cst.getName());
	}

	/**
	 * Utility method for testing the axioms of a context.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param ctx
	 *            a context root whose axioms will be tested.
	 * @param expected
	 *            the expected pretty-print axioms. The axioms are
	 *            "pretty-printed" as follows:
	 *            "label:predicateString:isTheorem". The order of the axioms is
	 *            important.
	 */
	protected void testContextAxioms(String msg, Context ctx,
			String... expected) {
		EList<Axiom> axms = ctx.getAxioms();
		assertEquals(msg + ": Incorrect number of axioms", expected.length,
				axms.size());
		Iterator<Axiom> iterator = axms.iterator();
		for (int i = 0; i < expected.length; i++) {
			testAxiom(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing an axiom.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param axm
	 *            the axiom under test.
	 * @param expected
	 *            the expected pretty print axiom. The axiom is "pretty-printed"
	 *            as follows: "label:predicateString:isTheorem".
	 */
	protected void testAxiom(String msg, Axiom axm, String expected) {
		assertEquals(msg + ": Incorrect axiom", expected, axm.getName()
				+ ":" + axm.getPredicate() + ":" + axm.isTheorem());
	}

	// =========================================================================
	// (END) Utility methods for testing Context elements.
	// =========================================================================

	// =========================================================================
	// (BEGIN) Utility methods for testing Machine elements.
	// =========================================================================

	/**
	 * Utility method for testing the REFINES clauses of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            a machine root whose REFINES clauses will be tested.
	 * @param expected
	 *            an array of expected REFINES clause. Each REFINES clause is
	 *            represented by its abstract machine name. The order of the
	 *            REFINES clauses is important.
	 */
	protected void testMachineRefinesClauses(String msg, Machine mch,
			String... expected) {
		EList<String> refinesNames = mch.getRefinesNames();
		assertSameStrings(msg,
				refinesNames.toArray(new String[refinesNames.size()]), expected);
	}

	/**
	 * Utility method for testing the SEES clauses of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            a machine root whose SEES clauses will be tested.
	 * @param expected
	 *            an array of expected SEES clause. Each SEES clause is
	 *            represented by its seen context name. The order of the SEES
	 *            clauses is important.
	 */
	protected void testMachineSeesClauses(String msg, Machine mch,
			String... expected) {
		EList<String> seesNames = mch.getSeesNames();
		assertSameStrings(msg,
				seesNames.toArray(new String[seesNames.size()]), expected);
	}

	/**
	 * Utility method for testing the variables of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            the machine root whose variables will be tested.
	 * @param expected
	 *            an array of expected variables. Each variable is represented
	 *            by its identifier. The order of the variables is important.
	 */
	protected void testMachineVariables(String msg, Machine mch,
			String... expected) {
		EList<Variable> vars = mch.getVariables();
		assertEquals(msg + ": Incorrect number of variables",
				expected.length, vars.size());
		Iterator<Variable> iterator = vars.iterator();
		for (int i = 0; i < expected.length; i++) {
			testVariable(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing the variables of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            the machine root whose variables will be tested.
	 * @param expected
	 *            an array of expected variables. Each variable is represented
	 *            by its identifier. The order of the variables is NOT
	 *            important.
	 */
	protected void testMachineVariablesUnordered(String msg, Machine mch,
			String... expected) {
		EList<Variable> vars = mch.getVariables();
		assertEquals(msg + ": Incorrect number of variables",
				expected.length, vars.size());
		for (int i = 0; i < expected.length; i++) {
			boolean b = false;
			for (Iterator<Variable> iterator = vars.iterator(); iterator
					.hasNext();) {
				Variable var = iterator.next();
				if (var.getName().equals(expected[i])) {
					b = true;
					break;
				}
			}
			if (!b) {
				fail("Variable " + expected[i] + " cannot be found");
			}
		}
	}

	/**
	 * Utility method for testing a variable.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param vrb
	 *            the variable under test.
	 * @param expected
	 *            the expected identifier of the variable.
	 */
	protected void testVariable(String msg, Variable vrb,
			String expected) {
		assertEquals(msg + ": Incorrect variable", expected,
				vrb.getName());
	}

	/**
	 * Utility method for testing the invariants of a context.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            a context root whose invariants will be tested.
	 * @param expected
	 *            the expected pretty-print invariants. The invariants are
	 *            "pretty-printed" as follows:
	 *            "label:predicateString:isTheorem". The order of the invariants
	 *            is important.
	 */
	protected void testMachineInvariants(String msg, Machine mch,
			String... expected) {
		EList<Invariant> invs = mch.getInvariants();
		assertEquals(msg + ": Incorrect number of invariants",
				expected.length, invs.size());
		Iterator<Invariant> iterator = invs.iterator();
		for (int i = 0; i < expected.length; i++) {
			testInvariant(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing an invariant.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param inv
	 *            the invariant under test.
	 * @param expected
	 *            the expected pretty-print invariant. The invariant is
	 *            "pretty-printed" as follows:
	 *            "label:predicateString:isTheorem".
	 */
	protected void testInvariant(String msg, Invariant inv,
			String expected) {
		assertEquals(msg + ": Incorrect invariant", expected,
				inv.getName() + ":" + inv.getPredicate() + ":"
						+ inv.isTheorem());
	}

	/**
	 * Utility method for testing the variants of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            a machine root whose variants will be tested.
	 * @param expected
	 *            the expected pretty-print variants. The variants are
	 *            "pretty-printed" as follows: "expressionString". The order of
	 *            the variants is important.
	 */
	protected void testMachineVariants(String msg, Machine mch,
			String... expected) {
		Variant variant = mch.getVariant();
		if (variant == null) {
			assertEquals(msg + ": Incorrect number of variants",
					expected.length, 0);
		} else {
			assertEquals(msg + ": Incorrect number of variants",
					expected.length, 1);
			testVariant(msg, variant, expected[0]);
		}
	}

	/**
	 * Utility method for testing a variant.
	 * 
	 * @param msg
	 *            a message for debugging
	 * @param var
	 *            the variant to be tested
	 * @param expected
	 *            the expected pretty-print variant. The variant are
	 *            "pretty-printed" as follows: "expressionString".
	 */
	protected void testVariant(String msg, Variant var, String expected) {
		assertEquals(msg + ": Incorrect expression string", expected,
				var.getExpression());
	}

	/**
	 * Utility method for testing the events of a machine.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param mch
	 *            a machine root whose events will be tested.
	 * @param expected
	 *            the expected pretty-print events (only the signature). The
	 *            events are "pretty-printed" as follows:
	 *            "label:convergent:isExtended". The order of the events is
	 *            important.
	 */
	protected void testMachineEvents(String msg, Machine mch,
			String... expected) {
		EList<Event> evts = mch.getEvents();
		assertEquals(msg + ": Incorrect number of events", expected.length,
				evts.size());
		Iterator<Event> iterator = evts.iterator();
		for (int i = 0; i < expected.length; i++) {
			testEvent(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            the event under test.
	 * @param expected
	 *            the expected pretty-print event (only the signature). The
	 *            event is "pretty-printed" as follows:
	 *            "label:convergent:isExtended".
	 */
	protected void testEvent(String msg, Event evt, String expected) {
		assertNotNull(msg + ": The event must not be null", evt);
		assertEquals(msg + ": Incorrect event", expected, evt.getName()
				+ ":" + evt.getConvergence() + ":" + evt.isExtended());
	}

	/**
	 * Utility method for testing the REFINES clauses of an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            an event whose REFINES clauses will be tested.
	 * @param expected
	 *            an array of expected REFINES clause. Each REFINES clause is
	 *            represented by its abstract event name. The order of the
	 *            REFINES clauses is important.
	 */
	protected void testEventRefinesClauses(String msg, Event evt,
			String... expected) {
		EList<String> refinesNames = evt.getRefinesNames();
		assertSameStrings(msg,
				refinesNames.toArray(new String[refinesNames.size()]), expected);
	}

	/**
	 * Utility method for testing the parameters of an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            an event whose parameters will be tested.
	 * @param expected
	 *            the expected set of parameters. Each parameter is represented
	 *            by its identifier. The order of the parameters is important.
	 */
	protected void testEventParameters(String msg, Event evt,
			String... expected) {
		EList<Parameter> pars = evt.getParameters();
		assertEquals(msg + ": Incorrect number of parameters",
				expected.length, pars.size());
		Iterator<Parameter> iterator = pars.iterator();
		for (int i = 0; i < expected.length; i++) {
			testParameter(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing a parameter.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param par
	 *            the parameter under test.
	 * @param expected
	 *            the expected parameter identifier.
	 */
	protected void testParameter(String msg, Parameter par,
			String expected) {
		assertEquals(msg + ": Incorrect parameter", expected,
				par.getName());
	}

	/**
	 * Utility method for testing the guards of an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            an event whose guards will be tested.
	 * @param expected
	 *            the expected pretty-print guards. The guards are
	 *            "pretty-printed" as follows:
	 *            "label:predicateString:isTheorem". The order of the guards is
	 *            important.
	 */
	protected void testEventGuards(String msg, Event evt,
			String... expected) {
		EList<Guard> grds = evt.getGuards();
		assertEquals(msg + ": Incorrect number of guards", expected.length,
				grds.size());
		Iterator<Guard> iterator = grds.iterator();
		for (int i = 0; i < expected.length; i++) {
			testGuard(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing a guard.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param grd
	 *            the guard under test.
	 * @param expected
	 *            the expected pretty-print guard. The guard is "pretty-printed"
	 *            as follows: "label:predicateString:isTheorem".
	 */
	protected void testGuard(String msg, Guard grd, String expected) {
		assertEquals(msg + ": Incorrect guard", expected, grd.getName()
				+ ":" + grd.getPredicate() + ":" + grd.isTheorem());
	}

	/**
	 * Utility method for testing the witnesses of an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            an event whose witnesses will be tested.
	 * @param expected
	 *            the expected pretty-print witnesses. The witnesses are
	 *            "pretty-printed" as follows: "label:predicateString". The
	 *            order of the witnesses is important.
	 */
	protected void testEventWitnesses(String msg, Event evt,
			String... expected) {
		EList<Witness> wits = evt.getWitnesses();
		assertEquals(msg + ": Incorrect number of witnesses",
				expected.length, wits.size());
		Iterator<Witness> iterator = wits.iterator();
		for (int i = 0; i < expected.length; i++) {
			testWitness(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing an witness.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param wit
	 *            the witness under test.
	 * @param expected
	 *            the expected pretty-print witness. The witness is
	 *            "pretty-printed" as follows: "label:predicateString".
	 */
	protected void testWitness(String msg, Witness wit, String expected) {
		assertEquals(msg + ": Incorrect witness", expected,
				wit.getName() + ":" + wit.getPredicate());
	}

	/**
	 * Utility method for testing the actions of an event.
	 * 
	 * @param msg
	 *            a message for debugging.
	 * @param evt
	 *            an event whose actions will be tested.
	 * @param expected
	 *            expected pretty-print actions. The actions are
	 *            "pretty-printed" as follows: "label:assignmentString". The
	 *            order of the actions is important.
	 */
	protected void testEventActions(String msg, Event evt,
			String... expected) {
		EList<Action> acts = evt.getActions();
		assertEquals(msg + ": Incorrect number of actions",
				expected.length, acts.size());
		Iterator<Action> iterator = acts.iterator();
		for (int i = 0; i < expected.length; i++) {
			testAction(msg, iterator.next(), expected[i]);
		}
	}

	/**
	 * Utility method for testing an action.
	 * 
	 * @param msg
	 *            a message
	 * @param act
	 *            the action under test
	 * @param expected
	 *            expected pretty-print action. The action is "pretty-printed"
	 *            as follows: "label:assignmentString".
	 */
	protected void testAction(String msg, Action act, String expected) {
		assertEquals(msg + ": Incorrect action", expected, act.getName()
				+ ":" + act.getAction());
	}

	// =========================================================================
	// (BEGIN) Utility methods for testing Machine elements.
	// =========================================================================

}
