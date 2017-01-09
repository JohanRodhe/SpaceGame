package com.ru.tgra.graphics.motion;

import com.ru.tgra.graphics.Point3D;
import com.ru.tgra.graphics.Vector3D;

import java.util.ArrayList;

/**
 * Created by johan on 2016-10-29.
 */
public class BSplineMotion {
    ArrayList<BezierMotion> motions;
    Point3D pStart;
    Point3D pEnd;
    float startTime;
    float endTime;

    public BSplineMotion(ArrayList<Point3D> controlPoints, float startTime, float endTime) {
        motions = new ArrayList<BezierMotion>();

        this.startTime = startTime;
        this.endTime = endTime;

        int motionCount = (controlPoints.size() / 2) - 1;
        float timePerMotion = (endTime - startTime) / (float)motionCount;

        if (controlPoints.size() < 4) {
            motions = null;
            return;
        }
        Point3D p1 = controlPoints.get(0);
        Point3D p2 = controlPoints.get(1);
        Point3D p3 = controlPoints.get(2);
        Point3D p4 = controlPoints.get(3);
        BezierMotion motion = new BezierMotion(p1, p2, p3, p4, startTime, startTime + timePerMotion);
        motions.add(motion);


        pStart = p1;

        for (int i = 1; i < motionCount; i++) {
            p1 = p4;
            p2 = p1;
            p2.add(Vector3D.difference(p4, p3));
            p3 = controlPoints.get((i + 1) * 2);
            p4 = controlPoints.get((i + 1) * 2 + 1);

            motion = new BezierMotion(p1, p2, p3, p4, startTime + timePerMotion * i, startTime + timePerMotion * (i + 1));
            motions.add(motion);
        }

        pEnd = p4;
    }

    public void getCurrentPos(float currentTime, Point3D outPos) {
        if (currentTime < startTime) {
            outPos.x = pStart.x;
            outPos.y = pStart.y;
            outPos.z = pStart.z;
        }
        else if (currentTime > endTime) {
            outPos.x = pEnd.x;
            outPos.y = pEnd.y;
            outPos.z = pEnd.z;
        }
        else {
            for (BezierMotion motion : motions) {
                if (motion.startTime < currentTime && currentTime < motion.endTime) {
                    motion.getCurrentPos(currentTime, outPos);
                    break;
                }
            }
        }
    }
}
