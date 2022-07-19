package org.lilac.srtp.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchInfo {
    private String[] tags;
    private Point[] points;
}
