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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import de.tudarmstadt.ukp.jwktl.parser.util.StringUtils;
import de.tudarmstadt.ukp.jwktl.parser.util.WordListProcessor;

class WordList implements Iterable<String> {
	@Deprecated
	private static final Pattern PATTERN_WORD = Pattern.compile("\\w+");

	final String comment;
	final List<String> words;

	WordList(String comment, List<String> words) {
		this.comment = isValid(comment) ? comment : null;
		this.words = words;
	}

	public int size() {
		return words.size();
	}


	public static WordList parse(/*final*/ String text) {// XXX SEH Temporary removed final from text arg in WordList.parse
		text =  StringUtils.strip(text, "* "); // XXX SEH Adding strip("* ") in WordList.parse
		String comment = null;
		List<String> result = new ArrayList<>();

		int braceStartIndex = text.indexOf("(''");
		if (braceStartIndex == -1) {
			braceStartIndex = text.indexOf("(");
		}

		int braceEndIndex;
		int curlyStartIndex = text.indexOf("{{");
		int curlyEndIndex = text.indexOf("}}");
		int endIndex = -1;
		/* XXX commented by SEH. To analyze !
		 *  if((braceStartIndex != -1 && curlyStartIndex == -1) || (braceStartIndex != -1 && braceStartIndex < curlyStartIndex)){
			int endOffset = 3;
			braceEndIndex = text.indexOf("'')", braceStartIndex);
			if(braceEndIndex == -1){
				System.err.println("1. braceEndIndex == -1"); //SEH
				braceEndIndex = text.indexOf(")", braceStartIndex);
				endOffset = 1;
			}
			if(braceEndIndex == -1){
				System.err.println("2. braceEndIndex == -1"); //SEH
				braceEndIndex = text.indexOf("''", braceStartIndex+3);
				endOffset = 2;
			}
			if (braceStartIndex + endOffset < braceEndIndex){
				System.err.println("braceStartIndex + endOffset < braceEndIndex"); //SEH
				String s = text.substring(braceStartIndex+endOffset, braceEndIndex);
				endIndex = braceEndIndex + endOffset;
				comment = s;
			}
		} else {
			//CM for preventing bug added third
			if(curlyStartIndex != -1 && curlyEndIndex != -1 && (curlyEndIndex >= curlyStartIndex)){
				int midIndex = text.indexOf('|',curlyStartIndex);
				if(midIndex != -1 && midIndex < curlyEndIndex){
					String templateName = text.substring(curlyStartIndex + 2, midIndex);
					if ("l".equals(templateName) || templateName.startsWith("l/")) {
						curlyEndIndex = -1;
					} else
						comment = text.substring(midIndex+1,curlyEndIndex);
				}else{
					comment = text.substring(curlyStartIndex+2,curlyEndIndex);
				}
				//startIndex = curlyStartIndex;		// bug fix: this would cause to jump into (startIndex>0) and parse the words before the curly brackets, i.e. empty syns or nothing
				endIndex = curlyEndIndex + 2;
				System.err.println("endIndex = curlyEndIndex + 2;"); //SEH
			}
		}*/

		WordListProcessor wordListFilter = new WordListProcessor();
		String relationStr;
		if (endIndex > 0 && endIndex < text.length())
			relationStr = text.substring(endIndex);
		else
		if (endIndex == -1) {
			//System.err.println("endIndex == -1"); // SEH
			relationStr = text;
		} else {
			//System.err.println("endIndex =/= -1");//SEH
			return new WordList(comment, result); //XXX WordList.words
		}
		result.addAll(wordListFilter.splitWordList(relationStr));//XXX WordList.words
		return new WordList(comment, result);//XXX WordList.words
	}

	@Override
	public Iterator<String> iterator() {
		return words.iterator();
	}

	private static boolean isValid(String comment) {
		return comment != null && PATTERN_WORD.matcher(comment).find();
	}
}
