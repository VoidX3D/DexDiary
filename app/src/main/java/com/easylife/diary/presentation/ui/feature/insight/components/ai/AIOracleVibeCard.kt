package com.easylife.diary.feature.insight.components.ai

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit // Placeholder for vibe icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easylife.diary.core.designsystem.theme.md_theme_primary
import com.easylife.diary.core.designsystem.theme.md_theme_onSurfaceVariant

/**
 * Created by erenalpaslan on 14.04.2026
 */
@Composable
fun AIOracleVibeCard(vibe: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.AcUnit, // Placeholder icon
                    contentDescription = "Vibe Icon",
                    tint = md_theme_primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Today's Vibe Check",
                    style = MaterialTheme.typography.titleMedium,
                    color = md_theme_primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = vibe,
                style = MaterialTheme.typography.bodyMedium,
                color = md_theme_onSurfaceVariant
            )
        }
    }
}
