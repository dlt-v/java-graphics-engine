package terrains;

import java.util.Random;

public class HeightsGenerator {
    private static final float AMPLITUDE = 70f;

    private Random random = new Random();
    private int seed;

    public HeightsGenerator() {
        this.seed = random.nextInt(10000);
    }

    public float generateHeight(int x, int z) {
        return getInterpolatedNoise(x / 8f, z / 8f) * AMPLITUDE;
    }

    private float interpolate(float a, float b, float blend) {
        double theta = blend * Math.PI;
        float f = (float) ((1f - Math.cos(theta)) * 0.5f);
        return a * (1f - f) + b * f;
    }

    private float getInterpolatedNoise(float x, float z) {
        int intX = (int) x;
        int intZ = (int) z;
        float fracX = x - intX;
        float fracZ = z - intZ;

        float v1 = getSmoothNoise(intX, intZ);
        float v2 = getSmoothNoise(intX + 1, intZ);
        float v3 = getSmoothNoise(intX, intZ + 1);
        float v4 = getSmoothNoise(intX + 1, intZ + 1);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    private float getSmoothNoise(int x, int z) {
        float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1) + getNoise(x + 1, z + 1)) / 16f;
        float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z + 1) + getNoise(x, z - 1)) / 8f;
        float center = getNoise(x, z) / 4f;
        return corners + sides + center;
    }

    private float getNoise(int x, int z) {
        random.setSeed(x * 12345L + z * 54321L + seed);
        return random.nextFloat() * 2f - 1f;
    }
}
