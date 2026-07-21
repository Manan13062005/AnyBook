package com.example.anybook.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.anybook.data.model.UserRole
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Card
import com.example.anybook.ui.components.AppButton

@Composable
fun RoleSelectorScreen(onRoleSelected: (UserRole) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Any Book",
                color = Color.Black,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Manage your appointments and business effortlessly. AnyBook helps clients and professionals stay organized and connected in one place.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Select your role",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        AppButton(
                            text = "Client",
                            modifier = Modifier.weight(1f).height(56.dp),
                            onClick = { onRoleSelected(UserRole.CLIENT) }
                        )

                        AppButton(
                            text = "Owner",
                            modifier = Modifier.weight(1f).height(56.dp),
                            onClick = { onRoleSelected(UserRole.OWNER) }
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun RoleSelectorScreenPreview(){
    RoleSelectorScreen(
        onRoleSelected = {}
    )
}
