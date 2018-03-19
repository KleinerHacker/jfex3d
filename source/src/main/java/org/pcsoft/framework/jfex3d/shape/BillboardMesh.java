package org.pcsoft.framework.jfex3d.shape;

import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;

public class BillboardMesh extends TriangleMesh {
    public BillboardMesh(double width, double height) {
        super(VertexFormat.POINT_TEXCOORD);
        rebuildPoints((float) width, (float) height);
    }

    void rebuildPoints(float width, float height) {
        getPoints().setAll(
                -width /2f, height /2f, 0f,
                -width /2f, -height /2f, 0f,
                width /2f, height /2f, 0f,
                width /2f, -height /2f, 0f
        );
        getTexCoords().setAll(
                1, 1,
                1, 0,
                0, 1,
                0, 0
        );
        getFaces().setAll(
                3, 2, 0, 1, 2, 0,
                3, 2, 1, 3, 0, 1
        );
    }
}
