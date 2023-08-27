package com.fernanortega.plantscommerce.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

val FlowerShape = object : Shape {
    @SuppressLint("RestrictedApi")
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val pathData = """M63.2049 32.7631L63.0781 32.9995L63.2049 33.2359C65.8154 38.1015 66.104 42.6731 64.5506 46.3364C62.9975 49.9991 59.5637 52.8462 54.5411 54.1857L54.2614 54.2603L54.1868 54.54C52.8468 59.5632 49.9994 62.9969 46.3366 64.5501C42.6731 66.1035 38.1015 65.8149 33.2358 63.2049L32.9995 63.0781L32.7631 63.2049C27.8975 65.8154 23.3258 66.104 19.6625 64.5506C15.9998 62.9975 13.1527 59.5637 11.8132 54.5411L11.7386 54.2614L11.4589 54.1868C6.43628 52.8468 3.00255 49.9994 1.44936 46.3366C-0.104048 42.6731 0.184556 38.1015 2.79507 33.2359L2.92189 32.9995L2.79507 32.7631C0.184555 27.8975 -0.104046 23.3258 1.44936 19.6625C3.00254 15.9998 6.43625 13.1527 11.4589 11.8132L11.7386 11.7386L11.8132 11.4589C13.1527 6.43625 15.9998 3.00254 19.6625 1.44936C23.3258 -0.104046 27.8975 0.184555 32.7631 2.79507L32.9995 2.92189L33.2359 2.79507C38.1015 0.184555 42.6731 -0.104045 46.3364 1.44936C49.9992 3.00254 52.8462 6.43625 54.1857 11.4589L54.2603 11.7386L54.54 11.8132C59.5632 13.1527 62.9969 15.9998 64.5501 19.6625C66.1035 23.3258 65.8149 27.8975 63.2049 32.7631Z"""
        val path = PathParser.createPathFromPathData(pathData).asComposePath()
        val pathSize = path.getBounds().size
        val matrix = android.graphics.Matrix().apply {
            postScale(
                size.width / pathSize.width, size.height / pathSize.height
            )
        }
        path.asAndroidPath().transform(matrix)

        return Outline.Generic(path)
    }
}