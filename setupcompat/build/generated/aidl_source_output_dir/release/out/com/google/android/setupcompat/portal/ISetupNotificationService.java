/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.google.android.setupcompat.portal;
/** Declares the interface for notification related service methods. */
public interface ISetupNotificationService extends android.os.IInterface
{
  /** Default implementation for ISetupNotificationService. */
  public static class Default implements com.google.android.setupcompat.portal.ISetupNotificationService
  {
    /** Register a notification without progress service */
    @Override public boolean registerNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException
    {
      return false;
    }
    @Override public void unregisterNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException
    {
    }
    /** Register a progress service */
    @Override public void registerProgressService(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle, com.google.android.setupcompat.portal.IPortalRegisterResultListener listener) throws android.os.RemoteException
    {
    }
    /** Checks the progress connection still alive or not. */
    @Override public boolean isProgressServiceAlive(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle) throws android.os.RemoteException
    {
      return false;
    }
    /** Checks portal avaailable or not. */
    @Override public boolean isPortalAvailable() throws android.os.RemoteException
    {
      return false;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.google.android.setupcompat.portal.ISetupNotificationService
  {
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.google.android.setupcompat.portal.ISetupNotificationService interface,
     * generating a proxy if needed.
     */
    public static com.google.android.setupcompat.portal.ISetupNotificationService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.google.android.setupcompat.portal.ISetupNotificationService))) {
        return ((com.google.android.setupcompat.portal.ISetupNotificationService)iin);
      }
      return new com.google.android.setupcompat.portal.ISetupNotificationService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
      }
      switch (code)
      {
        case TRANSACTION_registerNotification:
        {
          com.google.android.setupcompat.portal.NotificationComponent _arg0;
          _arg0 = _Parcel.readTypedObject(data, com.google.android.setupcompat.portal.NotificationComponent.CREATOR);
          boolean _result = this.registerNotification(_arg0);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_unregisterNotification:
        {
          com.google.android.setupcompat.portal.NotificationComponent _arg0;
          _arg0 = _Parcel.readTypedObject(data, com.google.android.setupcompat.portal.NotificationComponent.CREATOR);
          this.unregisterNotification(_arg0);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_registerProgressService:
        {
          com.google.android.setupcompat.portal.ProgressServiceComponent _arg0;
          _arg0 = _Parcel.readTypedObject(data, com.google.android.setupcompat.portal.ProgressServiceComponent.CREATOR);
          android.os.UserHandle _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.os.UserHandle.CREATOR);
          com.google.android.setupcompat.portal.IPortalRegisterResultListener _arg2;
          _arg2 = com.google.android.setupcompat.portal.IPortalRegisterResultListener.Stub.asInterface(data.readStrongBinder());
          this.registerProgressService(_arg0, _arg1, _arg2);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_isProgressServiceAlive:
        {
          com.google.android.setupcompat.portal.ProgressServiceComponent _arg0;
          _arg0 = _Parcel.readTypedObject(data, com.google.android.setupcompat.portal.ProgressServiceComponent.CREATOR);
          android.os.UserHandle _arg1;
          _arg1 = _Parcel.readTypedObject(data, android.os.UserHandle.CREATOR);
          boolean _result = this.isProgressServiceAlive(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        case TRANSACTION_isPortalAvailable:
        {
          boolean _result = this.isPortalAvailable();
          reply.writeNoException();
          reply.writeInt(((_result)?(1):(0)));
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements com.google.android.setupcompat.portal.ISetupNotificationService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      /** Register a notification without progress service */
      @Override public boolean registerNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, component, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerNotification, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public void unregisterNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, component, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_unregisterNotification, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /** Register a progress service */
      @Override public void registerProgressService(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle, com.google.android.setupcompat.portal.IPortalRegisterResultListener listener) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, component, 0);
          _Parcel.writeTypedObject(_data, userHandle, 0);
          _data.writeStrongInterface(listener);
          boolean _status = mRemote.transact(Stub.TRANSACTION_registerProgressService, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      /** Checks the progress connection still alive or not. */
      @Override public boolean isProgressServiceAlive(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, component, 0);
          _Parcel.writeTypedObject(_data, userHandle, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isProgressServiceAlive, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      /** Checks portal avaailable or not. */
      @Override public boolean isPortalAvailable() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        boolean _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_isPortalAvailable, _data, _reply, 0);
          _reply.readException();
          _result = (0!=_reply.readInt());
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
    }
    static final int TRANSACTION_registerNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_unregisterNotification = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_registerProgressService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_isProgressServiceAlive = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_isPortalAvailable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
  }
  public static final java.lang.String DESCRIPTOR = "com.google.android.setupcompat.portal.ISetupNotificationService";
  /** Register a notification without progress service */
  public boolean registerNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException;
  public void unregisterNotification(com.google.android.setupcompat.portal.NotificationComponent component) throws android.os.RemoteException;
  /** Register a progress service */
  public void registerProgressService(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle, com.google.android.setupcompat.portal.IPortalRegisterResultListener listener) throws android.os.RemoteException;
  /** Checks the progress connection still alive or not. */
  public boolean isProgressServiceAlive(com.google.android.setupcompat.portal.ProgressServiceComponent component, android.os.UserHandle userHandle) throws android.os.RemoteException;
  /** Checks portal avaailable or not. */
  public boolean isPortalAvailable() throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
