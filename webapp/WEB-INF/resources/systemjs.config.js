/**
 * System configuration for Angular samples
 * Adjust as necessary for your application needs.
 */
(function (global) {
  System.config({
      transpiler: 'typescript',
      typescriptOptions: {
          emitDecoratorMetadata: true
      },
      paths: {
      // paths serve as alias
      'npm:': 'resources/node_modules/'
    },
    // map tells the System loader where to look for things
    map: {
      // our app is within the app folder
      app: 'resources/app',

      // angular bundles
      '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
      '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
      '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
      '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
      '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
      '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
      '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',

      // other libraries
      'rxjs': 'npm:rxjs',
        'brace': 'npm:brace',
        'w3c-blob': 'npm:w3c-blob/index.js',
        'buffer': 'npm:buffer/index.js',
        'base64-js': 'npm:base64-js/index.js',
        'ieee754': 'npm:ieee754/index.js'
    },
    // packages tells the System loader how to load when no filename and/or no extension
    packages: {
      app: {
        main: './main.js',
        defaultExtension: 'js'
      },
      rxjs: {
        main: "Rx.js",
        defaultExtension: 'js'
      },
        brace: {
            main: './index.js',
            defaultExtension: 'js'
        }
    }
  });
})(this);
