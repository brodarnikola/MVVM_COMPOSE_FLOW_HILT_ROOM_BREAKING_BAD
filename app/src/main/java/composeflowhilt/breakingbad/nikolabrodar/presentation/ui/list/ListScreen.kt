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
package composeflowhilt.breakingbad.nikolabrodar.presentation.ui.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import composeflowhilt.breakingbad.nikolabrodar.R
import composeflowhilt.breakingbad.nikolabrodar.domain.model.Character
import composeflowhilt.breakingbad.nikolabrodar.presentation.ui.common.IconFavorite
import kotlin.math.ceil

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    select: (Character) -> Unit,
    moveToGoogleMpas: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val list = viewModel.list.collectAsState()
    val clickFavorite: (Character) -> Unit = viewModel::upsertFavorite
    Body(list, select, moveToGoogleMpas, clickFavorite, modifier)
}

@Composable
private fun Body(
    list: State<List<Character>>,
    select: (Character) -> Unit,
    moveToGoogleMpas: (Int) -> Unit,
    clickFavorite: (Character) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                stringResource(R.string.brba),
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h1,
                fontSize = 78.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = {
                    moveToGoogleMpas.invoke(7)
//                    val character = Character()
//                    select.invoke(character)
                          /*TODO*/ },

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    contentColor = Color.White)
                // Modifier.background(color = MaterialTheme.colors.primaryVariant,),
                // border = BorderStroke(width = 1.dp, brush = SolidColor(Color.Blue)),
                // below line is use to add shape for our button.
                // shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Google maps")
            }

        }
        StaggeredVerticalGrid(
            maxColumnWidth = 160.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            list.value.forEach {
                FeaturedList(
                    character = it, select = select,
                    clickFavorite = clickFavorite
                )
            }
        }
    }
}

@Composable
fun FeaturedList(
    character: Character,
    select: (Character) -> Unit,
    clickFavorite: (Character) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(4.dp),
//        color = MaterialTheme.colors.surface,
//        elevation = BrBaComposeTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        ConstraintLayout(
            modifier = Modifier.clickable { select.invoke(character) }
        ) {
            val (image, name, dim, favorite) = createRefs()
            Image(
                painter = rememberImagePainter(
                    data = character.img,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f / character.ratio)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )

            Box(
                modifier = Modifier
                    .constrainAs(dim) {
                        start.linkTo(image.start)
                        top.linkTo(image.top)
                        end.linkTo(image.end)
                        bottom.linkTo(image.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                    .background(
                        Brush.verticalGradient(
                            0.2f to Color.Transparent,
                            0.7f to Color(0x4d000000),
                        )
                    )
            ) { }

            IconFavorite(
                character.favorite,
                modifier = Modifier.constrainAs(favorite) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(parent.start, margin = 4.dp)
                }
            ) { clickFavorite.invoke(character) }

            Text(
                text = character.name,
                color = Color.White,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(image.bottom, margin = 8.dp)
                    }

            )
        }
    }
}

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}
