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

import com.github.fsteitz.extractor.token.ExtractionResult
import com.github.fsteitz.extractor.token.TokenExtractor
import com.github.fsteitz.extractor.token.matcher.LeoOrgTokenMatcher
import com.github.fsteitz.extractor.token.matcher.TokenMatcher
import com.github.fsteitz.extractor.token.reader.BookmarkReader
import com.github.fsteitz.extractor.token.serializer.TokenSerializer
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * @author Florian Steitz (florian@fsteitz.com)
 */

private const val BOOKMARK_JSON = "bookmark.json"
private const val TOKEN_JSON = "token.json"
private const val TOKEN_LOG_LIMIT = 50

private val charset = StandardCharsets.UTF_8 as Charset
private val matchers = listOf(
  LeoOrgTokenMatcher(charset)
)

/**
 *
 */
fun main(args: Array<String>) {
  val bookmarkFile = File(getClasspathResource(BOOKMARK_JSON).file)
  val urls = BookmarkReader.readFromFile(bookmarkFile, charset)
  val extractedTokens = TokenExtractor(matchers).extractTokens(urls)
  val tokenFilePath = bookmarkFile.parent + File.separator + TOKEN_JSON

  extractedTokens.forEach(::printResult)
  TokenSerializer(extractedTokens.values.flatMap { it.extractedTokens }).writeToDisk(tokenFilePath, charset)
}

/**
 *
 */
fun getClasspathResource(fileName: String): URL {
  return Thread.currentThread().contextClassLoader?.getResource(fileName)
    ?: throw FileNotFoundException("File '$fileName' in classpath not found")
}

/**
 *
 */
private fun printResult(matcher: TokenMatcher, extractionResult: ExtractionResult) {
  val matcherName = matcher.javaClass.simpleName

  println("Patterns applied by $matcherName:")
  matcher.patterns.forEach { println(" - $it") }

  println("\n${extractionResult.extractedTokens.size} Tokens extracted by $matcherName (max $TOKEN_LOG_LIMIT are shown):")
  extractionResult.extractedTokens.take(TOKEN_LOG_LIMIT).forEach { println(" - ${it.tokens}") }

  println("\n${extractionResult.nonMatchingInputStrings.size} input strings not matched by $matcherName:")
  extractionResult.nonMatchingInputStrings.forEach { println(" - $it") }
}