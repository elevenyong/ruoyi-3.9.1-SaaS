const req = require.context("../../assets/icons/png", false, /\.png$/);
const requireAll = (requireContext) => requireContext.keys();

const re = /\.\/(.*)\.png/;

const icons = requireAll(req).map((i) => {
  return i.match(re)[1];
});

export default icons;
