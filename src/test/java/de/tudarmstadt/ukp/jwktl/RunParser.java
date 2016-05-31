package de.tudarmstadt.ukp.jwktl;

import java.io.File;

import de.tudarmstadt.ukp.jwktl.JWKTL;
/**
 * 
 * @author Solene Eholie
 *
 */
public class RunParser {

	static String PATH_TO_DUMP_FILE = "/home/monordi/frwiktionary-latest-pages-articles.xml.bz2"; //"src/main/resources/enwiktionary-latest-pages-articles.xml.bz2";
	static String TARGET_DIRECTORY = "/home/monordi/frTarget";//"/home/monordi/enTarget";
	static boolean OVERWRITE_EXISTING_FILES = false;
	
	public static void main(String[] args) throws Exception {
		  File dumpFile = new File(PATH_TO_DUMP_FILE);
		  File outputDirectory = new File(TARGET_DIRECTORY);
		  boolean overwriteExisting = OVERWRITE_EXISTING_FILES;
		  
		  JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
		}
}
