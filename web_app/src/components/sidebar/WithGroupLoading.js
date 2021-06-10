import React from 'react';
import './withLoading.css';

function WithLoading(Component) {
  return function WihLoadingComponent({ isLoading, ...props }) {
    if (!isLoading) return <Component {...props} />;
    return (
      <div class="lds-ellipsis"><div></div><div></div><div></div><div></div></div>
    );
  };
}
export default WithLoading;