const handleHttpErrors = (response) => {
  if (!response.ok) {
    switch (response.status) {
      case 401:
      case 403:
        window.location.href = "/";
        return Promise.reject(response);
      case 400:
      case 409:
        return response.json().then((envelope) =>
          // eslint-disable-next-line prefer-promise-reject-errors
          Promise.reject({ inner: envelope.payload || envelope })
        );
      default:
        return Promise.reject(response);
    }
  }
  return response;
};

export const get = async (endpoint, options = {}) =>
  fetch(`${endpoint}`, {
    method: "GET",
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
  })
    .then(handleHttpErrors)
    .then((resp) => resp.json());
