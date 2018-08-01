// Generated by CoffeeScript 1.12.7
(function() {
  var Storage;

  Storage = (function() {
    function Storage() {}

    Storage.PREFIX_SIZE = 4;

    Storage.DOC_PREFIX = 'doc_';

    Storage.SET_PREFIX = 'set_';

    Storage.available = 'localStorage' in window;

    Storage.addDocument = function(name, obj, overwrite) {
      if (overwrite == null) {
        overwrite = true;
      }
      return this.addObject(this.DOC_PREFIX + name, obj, overwrite);
    };

    Storage.addSetting = function(name, str, overwrite) {
      if (overwrite == null) {
        overwrite = true;
      }
      return this.addString(this.SET_PREFIX + name, str, overwrite);
    };

    Storage.addObject = function(key, obj, overwrite) {
      if (overwrite == null) {
        overwrite = true;
      }
      return this.addString(key, JSON.stringify(obj), overwrite);
    };

    Storage.addString = function(key, str, overwrite) {
      if (overwrite == null) {
        overwrite = true;
      }
      if ((localStorage[key] != null) && !overwrite) {
        return false;
      }
      return localStorage[key] = str;
    };

    Storage.getDocument = function(name) {
      return this.getObject(this.DOC_PREFIX + name);
    };

    Storage.getSetting = function(name) {
      return this.getString(this.SET_PREFIX + name);
    };

    Storage.getObject = function(key) {
      return JSON.parse(this.getString(key));
    };

    Storage.getString = function(key) {
      if (localStorage[key] == null) {
        return null;
      }
      return localStorage[key];
    };

    Storage.listDocuments = function() {
      return this._listByPrefix(this.DOC_PREFIX);
    };

    Storage.removeDocument = function(name) {
      return this.remove(this.DOC_PREFIX + name);
    };

    Storage.removeSetting = function(name) {
      return this.remove(this.SET_PREFIX + name);
    };

    Storage.remove = function(key) {
      if (key in localStorage) {
        delete localStorage[key];
        return true;
      } else {
        return false;
      }
    };

    Storage.clearDocuments = function() {
      return this._clearByPrefix(this.DOC_PREFIX);
    };

    Storage.clearSettings = function() {
      return this._clearByPrefix(this.SET_PREFIX);
    };

    Storage._listByPrefix = function(prefix) {
      var k, list;
      list = [];
      for (k in localStorage) {
        if (k.substr(0, this.PREFIX_SIZE) === prefix) {
          list.push(k.substr(this.PREFIX_SIZE));
        }
      }
      return list;
    };

    Storage._clearByPrefix = function(prefix) {
      var e, i, len, list;
      list = this._listByPrefix(prefix);
      for (i = 0, len = list.length; i < len; i++) {
        e = list[i];
        delete localStorage[prefix + e];
      }
      return list.length;
    };

    return Storage;

  })();

  this.shdr || (this.shdr = {});

  this.shdr.Storage = Storage;

}).call(this);
