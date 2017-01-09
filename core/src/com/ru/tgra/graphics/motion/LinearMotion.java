package com.ru.tgra.graphics.motion;

import com.ru.tgra.graphics.Point3D;

/**
 * Created by johan on 2016-10-29.
 */
public class LinearMotion {
    Point3D p1;
    Point3D p2;
    float startTime;
    float endTime;

    public LinearMotion(Point3D p1, Point3D p2, float startTime, float endTime) {
        this.p1 = p1;
        this.p2 = p2;
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
            outPos.x = p2.x;
            outPos.y = p2.y;
            outPos.z = p2.z;
        }
        else {
            float t = (currentTime - startTime) / (endTime - startTime);
            outPos.x = (1.0f - t) * p1.x + t * p2.x;
            outPos.y = (1.0f - t) * p1.y + t * p2.y;
            outPos.z = (1.0f - t) * p1.z + t * p2.z;
        }
    }
}
