@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerTopBar(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel()
) {
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 20.dp),
        title = {
            TextBlack(
                text = stringResource(R.string.timer),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.onPrimary)
    )
}