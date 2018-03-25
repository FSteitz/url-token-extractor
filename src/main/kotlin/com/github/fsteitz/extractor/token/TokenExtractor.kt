/*
 * Copyright 2018 Florian Steitz
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
 */

package com.github.fsteitz.extractor.token

import com.github.fsteitz.extractor.token.matcher.TokenMatcher

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */
class TokenExtractor(val matchers: Collection<TokenMatcher>) {

  /**
   *
   */
  fun extractTokens(inputStrings: Collection<String>): Map<TokenMatcher, ExtractionResult> {
    return matchers.associateBy({ it }, { extractTokens(inputStrings, it) })
  }

  /**
   *
   */
  private fun extractTokens(inputStrings: Collection<String>, matcher: TokenMatcher): ExtractionResult {
    val extractedTokens = ArrayList<TokenData>()
    val nonMatchingInputStrings = ArrayList<String?>()

    inputStrings.forEach {
      val tokens = matcher.findTokens(it)

      if (tokens.isPresent) {
        extractedTokens.add(toTokenData(tokens.get()))
      } else {
        nonMatchingInputStrings.add(it)
      }
    }

    return ExtractionResult(extractedTokens, nonMatchingInputStrings)
  }

  /**
   *
   */
  private fun toTokenData(tokens: Collection<String?>): TokenData {
    val source = tokens.firstOrNull()
    return TokenData(source, tokens.minus(source))
  }
}