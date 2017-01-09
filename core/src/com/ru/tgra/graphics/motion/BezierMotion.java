package com.ru.tgra.graphics.motion;

import com.ru.tgra.graphics.Point3D;

/**
 * Created by johan on 2016-10-29.
 */
public class BezierMotion {
    Point3D p1;
    Point3D p2;
    Point3D p3;
    Point3D p4;
    float startTime;
    float endTime;

    public BezierMotion(Point3D p1, Point3D p2, Point3D p3, Point3D p4, float startTime, float endTime) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void getCurrentPos(float currentTime, Point3D outPos) {
        if (currentTime < startTime) {
            outPos.x = p1.x;
            outPos.y = p1.y;
            outPos.z = p1.z;
        }
        else if (currentTime > endTime) {
            outPos.x = p4.x;
            outPos.y = p4.y;
            outPos.z = p4.z;
        }
        else {
            float t = (currentTime - startTime) / (endTime - startTime);
            outPos.x = (float)Math.pow((1.0f - t), 3) * p1.x + 3 * (float)Math.pow((1.0f - t), 2) * t * p2.x + 3
                    * (float)Math.pow(t, 2) * (1.0f - t) * p3.x + (float)Math.pow(t, 3) * p4.x;
            outPos.y = (float)Math.pow((1.0f - t), 3) * p1.y + 3 * (float)Math.pow((1.0f - t), 2) * t * p2.y + 3
                    * (float)Math.pow(t, 2) * (1.0f - t) * p3.y + (float)Math.pow(t, 3) * p4.y;
            outPos.z = (float)Math.pow((1.0f - t), 3) * p1.z + 3 * (float)Math.pow((1.0f - t), 2) * t * p2.z + 3
                    * (float)Math.pow(t, 2) * (1.0f - t) * p3.z + (float)Math.pow(t, 3) * p4.z;
        }
    }
}
