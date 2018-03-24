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

package com.github.fsteitz.extractor.token.main

import com.github.fsteitz.extractor.token.matcher.LeoOrgTokenMatcher
import com.github.fsteitz.extractor.token.matcher.UrlTokenMatcher
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */

val charset = StandardCharsets.UTF_8 as Charset
val matchers = listOf(
        LeoOrgTokenMatcher(charset)
)

/**
 *
 */
fun main(args: Array<String>) {
  val testUrls = listOf(
          "http://dict.leo.org/ende?lp=ende&lang=en&searchLoc=0&cmpType=relaxed&sectHdr=on&spellToler=&search=my%20name%20is",
          "http://dict.leo.org/dictQuery/m-vocab/ende/de.html?searchLoc=0&lp=ende&lang=de&search=the%20despicable%20one&resultOrder=basic",
          "http://dict.leo.org/#/search=inexplicable&searchLoc=0&resultOrder=basic&multiwordShowSingle=on",
          "http://pda.leo.org/englisch-deutsch/Bayou",
          "https://dict.leo.org/englisch-deutsch/hit%20a%20snag"
  )

  matchers.forEach { extractTokens(testUrls, it) }
}

/**
 *
 */
private fun extractTokens(urls: Collection<String>, matcher: UrlTokenMatcher) {
  val matcherName = matcher.javaClass.simpleName

  println("Patterns checked by $matcherName:")
  matcher.patterns.forEach { println(" - $it") }

  println("\nTokens extracted by $matcherName:")
  urls.forEach { println(matcher.findTokens(it).orElse(listOf("?"))) }
}