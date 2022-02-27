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
package composeflowhilt.breakingbad.nikolabrodar.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import composeflowhilt.breakingbad.nikolabrodar.domain.model.Character
import composeflowhilt.breakingbad.nikolabrodar.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

open class BaseViewModel : ViewModel()

open class BaseFavoriteViewModel(private val updateFavoriteUseCase: UpdateFavoriteUseCase) :
    BaseViewModel() {

    fun upsertFavorite(character: Character) {
        updateFavoriteUseCase.execute(character)
            .flowOn(Dispatchers.IO)
            .catch { e -> e.printStackTrace() }
            .launchIn(viewModelScope)
    }
}
