import axios from 'axios';

const DEFAULT_ENDPOINT = "/cause/select";

const DEFAULT_PARAMETERS = {
  q: '*',
  start: 0,
  rows: 10
};

export default class CauseService {

  constructor(endpoint) {
    this._endpoint = endpoint || DEFAULT_ENDPOINT;
  }

  findById(id) {
    return this._getFirst({
      q: 'id:' + id
    });
  }

  findByName(name) {
    return this._makeRequest({
      q: name
    });
  }

  findByUri(uri) {
    return this._makeRequest({
      q: 'uri:' + uri
    });
  }

  // Generic find method
  find(params) {
    return this._makeRequest(params);
  }

  // Make a request with the given request params
  _makeRequest(requestParams) {
    let params = this._merge(requestParams);

    return axios.get(this._endpoint, {
      params: params
    }).then((resp) => {
      return resp.data;
    });
  }

  // Make a request and return the first document
  _getFirst(requestParams) {
    return this._makeRequest(requestParams).then((resp) => {
      return resp.docs[0];
    })
  }

  // Combines the given parameters with the default parameters
  _merge(newParams) {
    let params = {};
    for(let key in DEFAULT_PARAMETERS) {
      params[key] = DEFAULT_PARAMETERS[key];
    }
    for(let key in newParams) {
      params[key] = newParams[key];
    }

    return params;
  }

}





