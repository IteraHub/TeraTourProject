package com.itera.teratour;

import com.itera.teratour.model.TargetInfoModel;

/**
 * Created by root on 11/12/18.
 */

public interface IARTarget {

    TargetInfoModel OnTargetFound(String targetName, TargetInfoModel updateTargetDetail);
}
