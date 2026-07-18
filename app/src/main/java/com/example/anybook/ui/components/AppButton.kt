package com.example.anybook.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val ERROR_RED = Color(0xFFD32F2F)
private val DISABLED_GRAY = Color(0xFFBDBDBD)
private val SUCCESS_GREEN = Color(0xFF2E7D32)

enum class ButtonVariant {
    PRIMARY,
    OUTLINED,
    DESTRUCTIVE,
    SUCCESS,
    OUTLINED_DESTRUCTIVE
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    val shape = RoundedCornerShape(14.dp)
    val height = 48.dp

    when (variant) {
        ButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                modifier = modifier.fillMaxWidth().height(height),
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PRIMARY_BLUE,
                    disabledContainerColor = DISABLED_GRAY
                )
            ) {
                AppButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = Color.White
                )
            }
        }

        ButtonVariant.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                modifier = modifier.fillMaxWidth().height(height),
                shape = shape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PRIMARY_BLUE,
                    disabledContentColor = DISABLED_GRAY
                )
            ) {
                AppButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = PRIMARY_BLUE
                )
            }
        }

        ButtonVariant.DESTRUCTIVE -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                modifier = modifier.fillMaxWidth().height(height),
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ERROR_RED,
                    disabledContainerColor = DISABLED_GRAY
                )
            ) {
                AppButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = Color.White
                )
            }
        }

        ButtonVariant.SUCCESS -> {
            Button(
                onClick = onClick,
                enabled = enabled && !isLoading,
                modifier = modifier.fillMaxWidth().height(height),
                shape = shape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SUCCESS_GREEN,
                    disabledContainerColor = DISABLED_GRAY
                )
            ) {
                AppButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = Color.White
                )
            }
        }
        ButtonVariant.OUTLINED_DESTRUCTIVE -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled && !isLoading,
                modifier = modifier.fillMaxWidth().height(height),
                shape = shape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ERROR_RED,
                    disabledContentColor = DISABLED_GRAY
                )
            ) {
                AppButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = ERROR_RED
                )
            }
        }
    }
}

@Composable
private fun AppButtonContent(
    text: String,
    icon: ImageVector?,
    isLoading: Boolean,
    contentColor: Color
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = contentColor,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Please wait...",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Primary() {
    AppButton(text = "Register Business", onClick = {})
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Outlined() {
    AppButton(text = "Cancel Appointment", onClick = {}, variant = ButtonVariant.OUTLINED)
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Destructive() {
    AppButton(text = "Log Out", onClick = {}, variant = ButtonVariant.DESTRUCTIVE)
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Success() {
    AppButton(text = "Mark as Completed", onClick = {}, variant = ButtonVariant.SUCCESS)
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Disabled() {
    AppButton(text = "Add Service", onClick = {}, enabled = false)
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppButtonPreview_Loading() {
    AppButton(text = "Save Changes", onClick = {}, isLoading = true)
}