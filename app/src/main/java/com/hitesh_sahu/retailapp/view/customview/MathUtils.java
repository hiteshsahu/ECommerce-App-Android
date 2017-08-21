/*
 * Copyright (c) 2017. http://hiteshsahu.com- All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * If you use or distribute this project then you MUST ADD A COPY OF LICENCE
 * along with the project.
 *  Written by Hitesh Sahu <hiteshkrsahu@Gmail.com>, 2017.
 */
package com.hitesh_sahu.retailapp.view.customview;

import android.graphics.RectF;

/**
 * Helper class to perform math computations.
 */
public final class MathUtils {

    /**
     * Truncates a float number {@code f} to {@code decimalPlaces}.
     *
     * @param f             the number to be truncated.
     * @param decimalPlaces the amount of decimals that {@code f}
     *                      will be truncated to.
     * @return a truncated representation of {@code f}.
     */
    protected static float truncate(float f, int decimalPlaces) {
        float decimalShift = (float) Math.pow(10, decimalPlaces);
        return Math.round(f * decimalShift) / decimalShift;
    }


    /**
     * Checks whether two {@link RectF} have the same aspect ratio.
     *
     * @param r1 the first rect.
     * @param r2 the second rect.
     * @return {@code true} if both rectangles have the same aspect ratio,
     * {@code false} otherwise.
     */
    protected static boolean haveSameAspectRatio(RectF r1, RectF r2) {
        // Reduces precision to avoid problems when comparing aspect ratios.
        float srcRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r1), 3);
        float dstRectRatio = MathUtils.truncate(MathUtils.getRectRatio(r2), 3);

        // Compares aspect ratios that allows for a tolerance range of [0, 0.01] 
        return (Math.abs(srcRectRatio - dstRectRatio) <= 0.01f);
    }


    /**
     * Computes the aspect ratio of a given rect.
     *
     * @param rect the rect to have its aspect ratio computed.
     * @return the rect aspect ratio.
     */
    protected static float getRectRatio(RectF rect) {
        return rect.width() / rect.height();
    }
}
