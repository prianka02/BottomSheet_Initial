package com.example.bottomsheetexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bottomsheetexample.ui.theme.BottomSheetExampleTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomSheetExampleTheme {
                PersistentBottomSheetScaffold()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersistentBottomSheetScaffold() {

     val scaffoldState = rememberBottomSheetScaffoldState()

    // Get the screen height
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Calculate % of the screen height for peek height (15%)
    val peekHeight = screenHeight * 0.15f

    val bottomSheetHeight = screenHeight * 0.5f

    // Added a Box around the BottomSheet to apply the shadow
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(
                20.dp,
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .background(Color.White)
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = peekHeight,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContainerColor = Color.White,
            sheetDragHandle = {

                // Change handle icon
                when (scaffoldState.bottomSheetState.currentValue) {
                    SheetValue.PartiallyExpanded, SheetValue.Hidden -> {
                        CollapsedHandle()
                    }
                    SheetValue.Expanded -> {
                        ExpandedHandle()
                    }
                }
            },
            sheetShadowElevation = 20.dp,
            sheetContent = {

                // Defines the Bottom Sheet Content with scrollable LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f)
                        .padding(bottom = 50.dp)
                        .then(

                            // Half Screen Expanded Bottom Sheet Height
                            if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                                Modifier.height(bottomSheetHeight)
                            } else Modifier
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(bottom = 25.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Swipe up to expand sheet", fontWeight = FontWeight.Medium)
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(vertical = 50.dp)
                                .align(Alignment.CenterHorizontally),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            "Persistent Sheet Content",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Add scrollable items to the LazyColumn
                    items(peopleList) { person ->
                        PersonRow(
                            index = person.index,
                            name = person.name,
                            role = person.role
                        )
                    }
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text("Bottom Sheet Scaffold Example") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )
            },

            ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF1F1F1)),
                contentAlignment = Alignment.Center
            ) {
                Text("Scaffold Content")
            }
        }
    }
}

val peopleList = listOf(
    Person(0, "Louie Roob", "Principal Accountability Administrator"),
    Person(1, "Theresia Lehner", "District Data Officer"),
    Person(2, "Gina Lesch", "Director of Strategic Solutions"),
    Person(3, "Clyde Veum", "Regional Security Manager"),
    Person(4, "Jonathon Kunze", "Senior Marketing Strategist")
)

@Composable
fun CollapsedHandle() {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowUp,
        contentDescription = "Collapsed Bottom sheet arrow"
    )
}

@Composable
fun ExpandedHandle() {
    Icon(
        imageVector = Icons.Filled.KeyboardArrowDown,
        contentDescription = "Expanded Bottom sheet arrow"
    )
}
