package org.pcsoft.framework.jfex3d.shape;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.MeshView;

public class Billboard extends MeshView {
    private final DoubleProperty width = new SimpleDoubleProperty(1d);
    private final DoubleProperty height = new SimpleDoubleProperty(1d);

    public Billboard() {
        this(1d, 1d);
    }

    public Billboard(double width, double height) {
        super(new BillboardMesh(width, height));
        this.width.set(width);
        this.height.set(height);

        this.width.addListener(this::widthHeightInvalidated);
        this.height.addListener(this::widthHeightInvalidated);
    }

    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    private void widthHeightInvalidated(Observable observable) {
        ((BillboardMesh) getMesh()).rebuildPoints((float)this.width.get(), (float)this.height.get());
    }
}
