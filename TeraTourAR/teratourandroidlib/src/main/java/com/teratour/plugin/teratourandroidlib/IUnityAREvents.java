package com.teratour.plugin.teratourandroidlib;

/**
 * Created by root on 11/11/18.
 */

public interface IUnityAREvents {

    void OnRecordingStarted();

    void OnRecordingStopped();

    void OnShareVideo(String shareVideoPath);

    void OnDeleteVideo(String deleteVideoPath);

    void OnARTargetFound(String targetID);

    void OnARTargetLost(String targetID);
}
