//
//  ViewController.swift
//  InteropTest
//
//  Created by zhang on 12/30/15.
//  Copyright © 2015 zhang. All rights reserved.
//

import UIKit
import WebKit

class ViewController: UIViewController, WKScriptMessageHandler {
    
    var webView: WKWebView?;
    var rect: CGRect?

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let config = WKWebViewConfiguration();
        config.userContentController.addScriptMessageHandler(self, name: "interop");
        self.webView = WKWebView(frame: UIScreen.mainScreen().bounds, configuration: config);
        self.view = self.webView!;
        
        let url: NSURL = NSURL(string: "http://192.168.43.217:8088/interop/index.html")!;
        let request: NSURLRequest = NSURLRequest(URL: url);
        self.webView!.loadRequest(request);
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func userContentController(userContentController: WKUserContentController, didReceiveScriptMessage message: WKScriptMessage) {
        message.valueForKey("test");
    }
}

