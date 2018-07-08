// Generated by CoffeeScript 1.12.7
(function() {
  var Viewer;

  Viewer = (function() {
    Viewer.FRAGMENT = 0;

    Viewer.VERTEX = 1;

    function Viewer(dom, app) {
      this.dom = dom;
      this.app = app;
      this.time = 0.0;
      this.rotate = false;
      this.currentModel = null;
      this.rotateRate = 0.005;
      this.renderer = new THREE.WebGLRenderer({
        antialias: true
      });
      this.canvas = this.renderer.domElement;
      this.dom.appendChild(this.canvas);
      this.scene = new THREE.Scene();
      this.camera = new THREE.PerspectiveCamera(35, this.dom.clientWidth / this.dom.clientHeight, 1, 3000);
      this.controls = new THREE.OrbitControls(this.camera, this.dom);
      this.scene.add(this.camera);
      this.loader = new THREE.JSONLoader();
      this.material = this.defaultMaterial();
      this.loadModel('models/suzanne_high.js');
      this.onResize();
      window.addEventListener('resize', ((function(_this) {
        return function() {
          return _this.onResize();
        };
      })(this)), false);
    }

    Viewer.prototype.update = function() {
      this.controls.update();
      this.time += 0.001;
      this.uniforms.time.value = this.time;
      if (this.model && this.rotate) {
        this.model.rotation.y += this.rotateRate;
      }
      return this.renderer.render(this.scene, this.camera);
    };

    Viewer.prototype.reset = function() {
      return this.model.rotation.y = 0;
    };

    Viewer.prototype.onResize = function() {
      if (this.camera) {
        this.camera.aspect = this.dom.clientWidth / this.dom.clientHeight;
        this.camera.updateProjectionMatrix();
        this.camera.position.z = 900 / this.dom.clientWidth * 4;
        this.camera.lookAt(this.scene.position);
      }
      if (this.uniforms) {
        this.uniforms.resolution.value.x = this.dom.clientWidth;
        this.uniforms.resolution.value.y = this.dom.clientHeight;
      }
      return this.renderer.setSize(this.dom.clientWidth, this.dom.clientHeight);
    };

    Viewer.prototype.loadModel = function(key) {
      this.loader.load(key, (function(_this) {
        return function(geo) {
          return _this.initModel(geo, key);
        };
      })(this));
      return this.app.ui.showModelLoader();
    };

    Viewer.prototype.initModel = function(geo, key) {
      var data, old;
      this.currentModel = key;
      data = shdr.Models[key];
      if (this.model != null) {
        old = this.model.geometry;
        this.scene.remove(this.model);
        old.dispose();
      }
      this.model = new THREE.Mesh(geo, this.material);
      if (data != null) {
        if (data.scale != null) {
          this.model.scale.set(data.scale, data.scale, data.scale);
        }
      }
      this.scene.add(this.model);
      return this.app.ui.hideModelLoader();
    };

    Viewer.prototype.updateShader = function(shader, mode) {
      if (mode == null) {
        mode = Viewer.FRAGMENT;
      }
      if (mode === Viewer.FRAGMENT) {
        this.fs = shader;
        this.material.fragmentShader = shader;
      } else {
        this.vs = shader;
        this.material.vertexShader = shader;
      }
      return this.material.needsUpdate = true;
    };

    Viewer.prototype.defaultMaterial = function() {
      this.uniforms = {
        time: {
          type: 'f',
          value: 0.0
        },
        resolution: {
          type: 'v2',
          value: new THREE.Vector2(this.dom.clientWidth, this.dom.clientHeight)
        }
      };
      this.vs = shdr.Snippets.DefaultVertex;
      this.fs = shdr.Snippets.DefaultFragment;
      return new THREE.ShaderMaterial({
        uniforms: this.uniforms,
        vertexShader: this.vs,
        fragmentShader: this.fs
      });
    };

    return Viewer;

  })();

  this.shdr || (this.shdr = {});

  this.shdr.Viewer = Viewer;

}).call(this);
