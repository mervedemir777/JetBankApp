package com.example.jetbankapp.ui.view.home

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val gson = Gson()

    BackHandler {
        (context as? android.app.Activity)?.finish()
    }

    // States
    val state = viewModel.homeState.value
    val filteredBankDataList = viewModel.filteredBankDataList.value
    val searchQuery = remember { mutableStateOf("") }
    // Nav Controller
    val currentNavController = rememberUpdatedState(navController)

    val flagPainter: Painter = painterResource(id = R.drawable.flag)
    val flagEngPainter: Painter = painterResource(id = R.drawable.flageng)

    Scaffold(
        topBar = {
            Column {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { newValue ->
                        searchQuery.value = newValue
                        viewModel.filterBankDataList(newValue)
                    },
                    label = { Text(stringResource(R.string.search_by_city)) },
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 20.dp)
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = flagEngPainter,
                        contentDescription = "Change language to English",
                        modifier = Modifier
                            .clickable { changeLanguage(context, currentNavController, "en") }
                            .size(100.dp)

                    )
                    Image(
                        painter = flagPainter,
                        contentDescription = "Change language to Turkish",
                        modifier = Modifier
                            .clickable { changeLanguage(context, currentNavController, "tr") }
                            .size(100.dp)
                    )
                }
            }
        },
        content = { paddingValue ->

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation()
                }
            } else {
                if (!state.errorMessage.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorImage()
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
            ) {
                if (filteredBankDataList.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if(!state.isLoading) {
                                    NoDataImage()
                                }
                            }
                        }
                    }
                } else {
                    items(filteredBankDataList) { bankItem ->
                        BankDataCard(bankItem = bankItem) {
                            val bankDataJson = gson.toJson(bankItem)
                            val encodedBankDataJson = URLEncoder.encode(bankDataJson, StandardCharsets.UTF_8.toString())
                            navController.navigate("${AppScreen.DETAIL_SCREEN.name}/$encodedBankDataJson")
                        }
                    }
                }
            }
        }
    )

}

@Preview
@Composable
fun BankDataCard(
    bankItem: BankDataItem = BankDataItem(),
    onClickToCard: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable(onClick = onClickToCard)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            if (bankItem.bankBranch.isNullOrEmpty()) {
                BankDataCardItem(Icons.Default.AccountBalance, "${bankItem.bankDistrict} ŞUBESİ/${bankItem.bankCity}")
            } else {
                BankDataCardItem(Icons.Default.AccountBalance, bankItem.bankBranch)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "${bankItem.bankAddress}",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic
                )
            )
        }
    }
}

@Composable
fun BankDataCardItem(
    bankItemVector: ImageVector,
    bankItemText: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = bankItemVector,
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${bankItemText}",
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
    Spacer(modifier = Modifier.height(5.dp))
}

private fun changeLanguage(
    context: Context,
    currentNavController: State<NavController>,
    language: String
) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration()
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics);

    // Force UI recreation
    val currentDestination = currentNavController.value.currentDestination?.id
    currentNavController.value.popBackStack()
    currentDestination?.let { currentNavController.value.navigate(it) }
}