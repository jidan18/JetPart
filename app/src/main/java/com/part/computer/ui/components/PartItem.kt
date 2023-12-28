package com.part.computer.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.part.computer.R
import com.part.computer.ui.theme.JetPartTheme

@Composable
fun PartItem(
    image: Int,
    title: String,
    requiredPoint: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp))
            .heightIn(min = 200.dp) // Set a minimum height for the image

    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(190.dp)
                .heightIn(min = 150.dp) // Set a minimum height for the image
                .border(5.dp, Color(0xFF00BFA5), RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        )
        // Tambahkan padding ke semua teks
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = Modifier.padding(top = 8.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = stringResource(R.string.required_point, requiredPoint),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun RewardItemPreview() {
    JetPartTheme {
        PartItem(R.drawable.reward_4, "Penyimpanan SSD", 4000)
    }
}