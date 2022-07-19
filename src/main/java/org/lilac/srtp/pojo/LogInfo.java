package org.lilac.srtp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInfo {
    private String[] tags;
    private Point[] requestPoints;
    private Point[] returnPoints;

    public LogInfo(SearchInfo info, Point[] returnPoints){
        this.tags = info.getTags();
        this.requestPoints = info.getPoints();
        this.returnPoints = returnPoints;
    }
}
