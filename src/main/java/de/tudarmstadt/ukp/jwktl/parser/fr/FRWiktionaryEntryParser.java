package de.tudarmstadt.ukp.jwktl.parser.fr;

import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.entry.WiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import de.tudarmstadt.ukp.jwktl.parser.IWiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.WiktionaryEntryParser;
import de.tudarmstadt.ukp.jwktl.parser.components.CategoryHandler;
import de.tudarmstadt.ukp.jwktl.parser.components.InterwikiLinkHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DECollocationsHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEEntryLinkHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEPartOfSpeechHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseDefinitionHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DESenseExampleHandler;
import de.tudarmstadt.ukp.jwktl.parser.de.components.DEWordFormHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENDescendantRelationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENEntryFactory;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENEtymologyHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENPronunciationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENQuotationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENReferenceHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENRelationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENSemanticRelationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENSenseHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENTranslationHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENUsageNotesHandler;
import de.tudarmstadt.ukp.jwktl.parser.en.components.ENWordLanguageHandler;
import de.tudarmstadt.ukp.jwktl.parser.fr.components.FRPronunciationHandler;
import de.tudarmstadt.ukp.jwktl.parser.fr.components.FRSemanticRelationHandler;
import de.tudarmstadt.ukp.jwktl.parser.fr.components.FRSenseHandler;
import de.tudarmstadt.ukp.jwktl.parser.fr.components.FRTranslationHandler;
import de.tudarmstadt.ukp.jwktl.parser.fr.components.FRWordFormHandler;
import de.tudarmstadt.ukp.jwktl.parser.util.ParsingContext;

/**
 * An implementation of the {@link IWiktionaryEntryParser} interface for 
 * parsing the contents of article pages from the French Wiktionary. 
 * @author Solene Eholie
 * TODO Fill FR WiktionaryEntryParser
 */
public class FRWiktionaryEntryParser extends WiktionaryEntryParser {

	/** Initializes the French entry parser. That is, the language and the
	 *  redirection pattern is defined, and the handlers for extracting
	 *  the information from the article constituents are registered. */
	public FRWiktionaryEntryParser() {
		super(Language.FRENCH, "REDIRECT"); // TODO SEH specify the language-specific prefix used for redirections
		
		// Fixed name content handlers.
		register(new FRSemanticRelationHandler(RelationType.SYNONYM, "S|synonymes", "S|syn")); // SEH en-de // S|synonymes
		//register(new ENSemanticRelationHandler(RelationType.ANTONYM, "S|antonymes")); // SEH en-de // S|antonymes
		register(new FRSemanticRelationHandler(RelationType.HYPERNYM, "S|hyperonymes", "S|hyper")); // SEH en-de // S|hyperonymes
		register(new FRSemanticRelationHandler(RelationType.HYPONYM, "S|hyponymes", "S|hypo")); // SEH en-de // S|hyponymes
		//register(new ENSemanticRelationHandler(RelationType.HOLONYM, "S|holonymes, "S|holo"));
		//register(new ENSemanticRelationHandler(RelationType.MERONYM, "S|méronymes", "S|méro"));
		//register(new ENSemanticRelationHandler(RelationType.TROPONYM, "S|troponymes", "S|tropo"));
		//register(new ENSemanticRelationHandler(RelationType.COORDINATE_TERM, "Coordinate terms"));
		//register(new ENSemanticRelationHandler(RelationType.SEE_ALSO, "See also"));
		//register(new ENRelationHandler(RelationType.DERIVED_TERM, "S|dérivés")); // SEH en-de // S|dérivés
		//register(new ENRelationHandler(RelationType.ETYMOLOGICALLY_RELATED_TERM, "Related terms")); // SEH en-de 
		//register(new ENDescendantRelationHandler("Descendants"));
		register(new FRTranslationHandler()); // SEH en-de // S|traductions
		//register(new ENEtymologyHandler()); //SEH en-de
		//register(new ENReferenceHandler()); //SEH en-de
		//register(new ENQuotationHandler()); //SEH en-de
		// * register(new FRPronunciationHandler()); //SEH en-de // S|prononciation
		//register(new ENUsageNotesHandler());
		// SEH de : register(new DESenseDefinitionHandler());
		// SEH de : register(new DECollocationsHandler());
		// SEH de : register(new DESenseExampleHandler());

		// Pattern
		//register(new CategoryHandler("Category")); //SEH en-de
		//register(new InterwikiLinkHandler("Category")); //SEH en-de
		//register(new ENWordLanguageHandler()); //SEH en-de
		register(new FRSenseHandler());
		// SEH de : register(new DEPartOfSpeechHandler());
		// SEH de : register(new DEEntryLinkHandler());
	}
	
	@Override
	protected ParsingContext createParsingContext(final WiktionaryPage page) {
		return new ParsingContext(page, new ENEntryFactory());
	}

	/** Checks if it is start of new section. Symbols are =, [[ */
	protected boolean isStartOfBlock(String line) { // XXX SEH isStartOfBlock 
		line = line.trim();
		/* EN
		 if (line.startsWith("----")) {
			return true;
		}
		if (line.startsWith("="))
			return true;
		if (line.startsWith("[[") && line.endsWith("]]"))
			return true;
		return false;
		*/
		
		/* DE 
		return line.startsWith("=")
				|| line.startsWith("{{")
				|| line.startsWith("[[") 
				|| (line.startsWith("'''") && line.endsWith("'''"));
		*/
		//System.err.println(line); //SEH
		return line.startsWith("=")
				|| (line.startsWith("[[") && line.endsWith("]]")) ;
	}

}
