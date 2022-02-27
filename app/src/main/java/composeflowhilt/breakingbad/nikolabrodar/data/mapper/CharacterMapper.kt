/*
 * Copyright 2021 nikolabrodar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package composeflowhilt.breakingbad.nikolabrodar.data.mapper

import composeflowhilt.breakingbad.nikolabrodar.data.local.model.CharacterEntity
import composeflowhilt.breakingbad.nikolabrodar.data.remote.model.CharacterResponse
import composeflowhilt.breakingbad.nikolabrodar.domain.model.Character

fun CharacterResponse.toCharacter() = Character(
    charId = charId,
    name = name,
    birthday = birthday,
    occupation = occupation,
    img = img,
    status = status,
    nickname = nickname,
    appearance = appearance,
    portrayed = portrayed,
    category = category,
    betterCallSaulAppearance = betterCallSaulAppearance
)

fun CharacterEntity.toCharacter() = Character(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname,
    favorite = favorite,
    ctime = ctime
)

fun Character.toCharacterEntity() = CharacterEntity(
    charId = charId,
    name = name,
    img = img,
    nickname = nickname
)
