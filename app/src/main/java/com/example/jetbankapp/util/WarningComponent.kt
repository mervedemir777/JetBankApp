package com.example.jetbankapp.util


@Composable
fun ErrorImage() {
    val errorPainter: Painter = painterResource(id = R.drawable.error)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = errorPainter,
            contentDescription = "Error Image",
            modifier = Modifier.fillMaxSize().padding(10.dp)
        )
    }
}

@Composable
fun NoDataImage() {

    val noDataPainter: Painter = painterResource(id = R.drawable.nodata)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = noDataPainter,
            contentDescription = "No Data Image",
            modifier = Modifier.fillMaxSize().padding(10.dp)
        )
    }
}

@Composable
fun NoInternetDialog(internetConnectionFlow: Flow<Boolean>) {
    val context = LocalContext.current
    val isConnected by internetConnectionFlow.collectAsState(initial = isNetworkAvailable(context))
    val showDialog = remember { mutableStateOf(!isConnected) }

    if (!isConnected) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("İnternet Bağlantısı Yok") },
            text = { Text("Lütfen internet bağlantınızı kontrol edin ve tekrar deneyin.") },
            confirmButton = {
                TextButton(onClick = {
                    if (isNetworkAvailable(context)) {
                        showDialog.value = false
                    }
                }) {
                    Text("Tekrar Deneyin")
                }
            }
        )
    }
}

@Composable
fun LoadingAnimation() {
    var isPlaying by remember {
        mutableStateOf(true)
    }

    var speed by remember {
        mutableFloatStateOf(1f)
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.bankloading)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center
        )
    }
}