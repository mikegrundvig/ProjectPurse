// Copyright 2015-2016 Espressif Systems (Shanghai) PTE LTD
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
#include "esp_http_server.h"
#include "esp_camera.h"
#include "Arduino.h"

httpd_handle_t camera_httpd = NULL;

static esp_err_t status_handler(httpd_req_t *req){
    httpd_resp_set_hdr(req, "Access-Control-Allow-Origin", "*");
    return httpd_resp_send(req, NULL, 0);
}

// Use the camera to grab an image and stream it back
static esp_err_t image_handler(httpd_req_t *req){

    // Turn on the flash, the delay is to give the camera time to adjust to it
    digitalWrite(4, HIGH);
    delay(500);
    
    camera_fb_t * fb = NULL;
    esp_err_t res = ESP_OK;

    // Get the image from the camera
    fb = esp_camera_fb_get();
    if (!fb) {
        Serial.println("Camera capture failed");
        httpd_resp_send_500(req);
        return ESP_FAIL;
    }

    // Stream down the image to the caller
    httpd_resp_set_type(req, "image/jpeg");
    httpd_resp_set_hdr(req, "Content-Disposition", "inline; filename=capture.jpg");
    httpd_resp_set_hdr(req, "Access-Control-Allow-Origin", "*");
    res = httpd_resp_send(req, (const char *)fb->buf, fb->len);

    // Release the memory used by the camera capture
    esp_camera_fb_return(fb);

    // Turn the light back off
    digitalWrite(4, LOW);
    
    return res;
}

// Start the camera server proper
void startCameraServer(){
    httpd_config_t config = HTTPD_DEFAULT_CONFIG();

    // Set up the two endpoints we expose
    httpd_uri_t image_uri = {
        .uri       = "/image",
        .method    = HTTP_GET,
        .handler   = image_handler,
        .user_ctx  = NULL
    };

    httpd_uri_t status_uri = {
        .uri       = "/status",
        .method    = HTTP_GET,
        .handler   = status_handler,
        .user_ctx  = NULL
    };


    // Fire up the server and start listening for requests
    Serial.printf("Starting web server...");
    if (httpd_start(&camera_httpd, &config) == ESP_OK) {
        httpd_register_uri_handler(camera_httpd, &image_uri);
        httpd_register_uri_handler(camera_httpd, &status_uri);
    }
}
