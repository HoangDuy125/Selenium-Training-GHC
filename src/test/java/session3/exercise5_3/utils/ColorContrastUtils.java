package session3.exercise5_3.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ColorContrastUtils {
    private static final Pattern RGB_PATTERN = Pattern.compile("rgba?\\((\\d+),\\s*(\\d+),\\s*(\\d+)");

    private ColorContrastUtils() {
    }

    public static double contrastRatio(String foregroundCssColor, String backgroundCssColor) {
        int[] fg = parseRgb(foregroundCssColor);
        int[] bg = parseRgb(backgroundCssColor);

        double l1 = relativeLuminance(fg[0], fg[1], fg[2]);
        double l2 = relativeLuminance(bg[0], bg[1], bg[2]);

        double lighter = Math.max(l1, l2);
        double darker = Math.min(l1, l2);
        return (lighter + 0.05) / (darker + 0.05);
    }

    private static int[] parseRgb(String cssColor) {
        Matcher matcher = RGB_PATTERN.matcher(cssColor);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Unsupported color format: " + cssColor);
        }
        return new int[]{
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
        };
    }

    private static double relativeLuminance(int r, int g, int b) {
        double rs = normalizeChannel(r);
        double gs = normalizeChannel(g);
        double bs = normalizeChannel(b);
        return (0.2126 * rs) + (0.7152 * gs) + (0.0722 * bs);
    }

    private static double normalizeChannel(int channel) {
        double normalized = channel / 255.0;
        if (normalized <= 0.03928) {
            return normalized / 12.92;
        }
        return Math.pow((normalized + 0.055) / 1.055, 2.4);
    }
}
