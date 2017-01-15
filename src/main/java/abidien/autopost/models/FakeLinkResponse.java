package abidien.autopost.models;

/**
 * Created by ABIDIEN on 14/01/2017.
 */
public class FakeLinkResponse extends FakeLinkEntity {
    public FakeLinkResponse(FakeLinkEntity fakeLinkEntity, int click) {
        super(fakeLinkEntity);
        this.click = click;
    }

    private int click;

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }
}
