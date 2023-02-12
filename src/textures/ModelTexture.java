package textures;

public class ModelTexture {
    private int textureId;

    private float shineDamper = .3f;
    private float reflectivity = 0;

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }



    public ModelTexture(int id) {
        this.textureId = id;
    }
    public int getId() {
        return this.textureId;
    }
}
