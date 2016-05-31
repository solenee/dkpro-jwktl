/*******************************************************************************
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.jwktl.parser.fr.components;

import java.util.Iterator;
import java.util.List;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionarySense;
import de.tudarmstadt.ukp.jwktl.parser.fr.FRWiktionaryEntryParserTest;
import static de.tudarmstadt.ukp.jwktl.api.RelationType.SYNONYM;
import static de.tudarmstadt.ukp.jwktl.api.RelationType.HYPERNYM;
import static de.tudarmstadt.ukp.jwktl.api.RelationType.HYPONYM;
import static de.tudarmstadt.ukp.jwktl.parser.en.components.ENSemanticRelationHandler.findMatchingSense;

/**
 * Test parser for relations : synonym, hyponym, hypernym
 * accueil.txt; aine.txt, manga.txt, ablation.txt, 
 * @author Solene Eholie
 *
 */
public class FRSemanticRelationHandlerTest extends FRWiktionaryEntryParserTest {
	/***/
	public void testSynonymsAccueil() throws Exception {
		IWiktionaryPage page = parse("accueil.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "home", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "main page", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "page d’accueil", iter.next());
		assertFalse(iter.hasNext());
	}
	
	public void testCompleteMedecin() throws Exception {
		IWiktionaryPage page = parse("médecin.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "docteur", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "praticien", iter.next());
		assertFalse(iter.hasNext());
		
		iter = entry.getUnassignedSense()
				.getRelations(HYPERNYM).iterator();
		assertRelation(HYPERNYM, "métier", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPERNYM, "profession", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPERNYM, "santé", iter.next());
		assertFalse(iter.hasNext());
	}
	
	/**
	 * TODO Test robustess with regard to enumeration 1., 2. etc
	 * @throws Exception
	 */ /*
	public void testSynonymsRobustessAine() throws Exception {
		IWiktionaryPage page = parse("aine.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		//SYNONYM : [SYNONYM:alignette, SYNONYM:alinette, SYNONYM:ainette, SYNONYM:ainet, SYNONYM:1. materia, SYNONYM:2. kouluaine, SYNONYM:oppiaine, SYNONYM:3. kirjoitelma, SYNONYM:kouluaine, SYNONYM:essee, SYNONYM:4. kama]
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(SYNONYM).iterator();
		assertRelation(SYNONYM, "alignette", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "alinette", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "ainette", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "ainet", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "1. materia", iter.next());
		assertTrue(iter.hasNext());
		//2. kouluaine, SYNONYM:oppiaine, SYNONYM:3. kirjoitelma, SYNONYM:kouluaine, 
		assertRelation(SYNONYM, "2. kouluaine", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "oppiaine", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "3. kirjoitelmae", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "kouluaine", iter.next());
		assertTrue(iter.hasNext());
		//SYNONYM:essee, SYNONYM:4. kama]
		assertRelation(SYNONYM, "essee", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(SYNONYM, "4. kama", iter.next());
		assertFalse(iter.hasNext());
	}*/
	
	/***/
	public void testHypernymsManga() throws Exception {
		IWiktionaryPage page = parse("manga.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(HYPERNYM).iterator();
		assertRelation(HYPERNYM, "bande dessinée", iter.next());
		assertFalse(iter.hasNext());
	}
	
	/***/
	public void testHyponymsManga() throws Exception {
		IWiktionaryPage page = parse("manga.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(HYPONYM).iterator();
		assertRelation(HYPONYM, "josei‎", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "redikomi", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "seinen", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "shōjo", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "shōnen", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "yaoi", iter.next());
		assertFalse(iter.hasNext());
	}
	
	/**
	 * Test robustess with regard to multiple * 
	 * @throws Exception
	 *//*
	public void testHyponymsRobustessAblation() throws Exception {
		IWiktionaryPage page = parse("ablation.txt");
		IWiktionaryEntry entry = page.getEntry(0);
		Iterator<IWiktionaryRelation> iter = entry.getUnassignedSense()
				.getRelations(HYPONYM).iterator();
		assertRelation(HYPONYM, "amputation", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "castration", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "excision", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "mutilation", iter.next());
		assertTrue(iter.hasNext());
		assertRelation(HYPONYM, "résection", iter.next());
		assertFalse(iter.hasNext());
	}*/

	protected static void assertRelation(final RelationType relationType,
			final String target, final IWiktionaryRelation actual) {
		assertEquals(relationType, actual.getRelationType());
		assertEquals(target, actual.getTarget());
		System.out.print(actual.getRelationType()+"--"+actual.getTarget()+"\n");
	}
}

