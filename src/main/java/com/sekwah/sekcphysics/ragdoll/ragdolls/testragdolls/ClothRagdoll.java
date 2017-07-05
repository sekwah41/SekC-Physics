package com.sekwah.sekcphysics.ragdoll.ragdolls.testragdolls;

import com.sekwah.sekcphysics.ragdoll.ragdolls.BaseRagdoll;
import com.sekwah.sekcphysics.ragdoll.parts.AnchoredSkeletonPoint;
import com.sekwah.sekcphysics.ragdoll.parts.Constraint;
import com.sekwah.sekcphysics.ragdoll.parts.SkeletonPoint;

/**
 * Created by sekawh on 8/5/2015.
 */
public class ClothRagdoll extends BaseRagdoll {

    private int width = 30;

    private int height = 30;

    private float spacing = 0.20f;

    SkeletonPoint[][] points = new SkeletonPoint[width][height];

    public ClothRagdoll() {
        super(1.4f);

        centerHeightOffset = 24;

        // Top row (anchor points)
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(y == 0) {
                    points[x][y] = new AnchoredSkeletonPoint(x * spacing, -y * spacing, 0, false);
                }
                else{
                    points[x][y] = new SkeletonPoint(x * spacing, -y * spacing, 0, false);
                }
                skeleton.points.add(points[x][y]);
            }
        }

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(x < width - 1) {
                    skeleton.constraints.add(new Constraint(points[x][y],points[x + 1][y]));
                }
                if(y < height - 1) {
                    skeleton.constraints.add(new Constraint(points[x][y],points[x][y + 1]));
                }
            }
        }
    }

}
