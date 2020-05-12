package net.lldv.pydow.quadplots.components.math;

import com.nukkitx.math.vector.Vector3i;

public class PlotVector3i {

    public int x, y, z;

    public PlotVector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlotVector3i(Vector3i v) {
        x = v.getX();
        y = v.getY();
        z = v.getZ();
    }

    public Vector3i toVector3i() {
        return Vector3i.from(x, y, z);
    }

    public PlotVector3i subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public PlotVector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

}
